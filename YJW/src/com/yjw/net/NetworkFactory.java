/*
 * 
 *     Created by 亦恒 陶 
 *  Copyright (c) 2012年 . All rights reserved.
 *  
 */
package com.yjw.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import android.os.Message;
import android.util.Log;

import com.yjw.thread.delayed.ShowMessageThread;
import com.yjw.util.Util;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class NetworkFactory {
	public enum PostType{HTTP,HTTPS};
	
	public PostType postType=PostType.HTTPS;
	
	public static NetworkFactory instance = null;
	public static final int ReconnectTime = 20000;	//重连时间：20秒
	public static synchronized NetworkFactory getInstance() {
		if (instance == null) {
			instance = new NetworkFactory();
		}
		return instance;
	}
	
	private HttpClient getDefaultHttpClient() {
		HttpClient httpClient;
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,	3000);
		httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		return httpClient;
	}

	private String doPost(String basicURL, List<BasicNameValuePair> parameters) {
		String responseText = "";
		String http=NetworkConstants.SERVER_URL+basicURL;
		Log.d("@Post", http);
		Log.d("@Parameters",parameters.toString());
		try {
			HttpEntity httpentity = new UrlEncodedFormEntity(parameters, "UTF8");
			HttpPost post = new HttpPost(http);
			post.setEntity(httpentity);
			HttpResponse response = this.getDefaultHttpClient().execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream iStream = response.getEntity().getContent();
				BufferedReader bfReader = new BufferedReader(new InputStreamReader(iStream));
				String str;
				while ((str = bfReader.readLine()) != null) {
					responseText = responseText + str;
				}
				Log.d("@Receive", responseText);
			} else {
				Log.e("@Receive", "error status code"	+ response.getStatusLine().getStatusCode());
			}
		} catch(Exception e){
			Log.e("@Post",e.getMessage());
			return null;
		}
		return responseText;
	}

	public String doPost(String basicURL, List<BasicNameValuePair> parameters,boolean flag){
		//flag:是否重连
		String str;
		if (postType==PostType.HTTP) 
			str=doPost(basicURL,parameters);
		else 
			str=doSSLPost(basicURL,parameters);
		if(str==null){
			if(flag){
				for (int i=0;i<5;++i){
					Message msg = Message.obtain();
					msg.what = YJWMessage.NET_FAIL_RECONNECT.ordinal();
					msg.arg1=i;
					ShowMessageThread.mHandler.sendMessage(msg);
					try {
						Thread.sleep(NetworkFactory.ReconnectTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (postType==PostType.HTTP) 
						str=doPost(basicURL,parameters);
					else 
						str=doSSLPost(basicURL,parameters);
				}
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
			else {
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
		}
		return str;
	}
	
	public String doSSLPost(String basicURL, List<BasicNameValuePair> parameters){
			String https = NetworkConstants.SERVER_URL_HTTPS+basicURL;//https　uri  
			String query = "";
			boolean start=true;
			for (BasicNameValuePair pair:parameters){
				if (start){
					start=false;
				}else{
					query+="&";
				}
				query+=pair.getName()+"="+pair.getValue();				
			}
	        byte[] entitydata = query.getBytes();  
	        try{  
	        	Log.d("@URL", https);
	        	Log.d("@Parameters", query);
	        	SSLContext sc = SSLContext.getInstance("TLS");  
	        	sc.init(null, new TrustManager[]{new EasyTrustManager()}, new SecureRandom());  
	        	HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
				HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
					@Override public boolean verify(String hostname, SSLSession session) {return true;}
					});  
				HttpsURLConnection conn = (HttpsURLConnection)new URL(https).openConnection();  
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");  
				conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
				conn.setDoOutput(true);  
				conn.setDoInput(true);
				conn.connect();
				
				OutputStream outStream = conn.getOutputStream();  
				outStream.write(entitydata);
				outStream.flush();  
				outStream.close();

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
				StringBuffer sb = new StringBuffer();  
				String line;  
				while ((line = br.readLine()) != null)  
	                 sb.append(line);
				Log.d("@Receive",sb.toString());
				return sb.toString(); 
	 		}catch(Exception e){  
	 			if (e!=null){
	 				if (e.getMessage()!=null) Log.e("@Receive", e.getMessage());
	 				else e.printStackTrace();
	 			}
	 			return null;
	        }	          
	}  
	
	public String doSSLPostData(String basicURL, byte[] data){
		String https = NetworkConstants.SERVER_URL_HTTPS+basicURL;//https　uri  
        try{  
        	Log.d("@URL", https);
        	Log.d("@Parameters", "[data] length:"+data.length);
        	SSLContext sc = SSLContext.getInstance("TLS");  
        	sc.init(null, new TrustManager[]{new EasyTrustManager()}, new SecureRandom());  
        	HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
				@Override public boolean verify(String hostname, SSLSession session) {return true;}
				});  
			HttpsURLConnection conn = (HttpsURLConnection)new URL(https).openConnection();  
			conn.setRequestProperty("Content-Type","image/png;charset=UTF-8");  
			conn.setRequestProperty("Content-Length", String.valueOf(data.length+4));
			conn.setDoOutput(true);  
			conn.setDoInput(true);  
			conn.connect();
			int len=data.length;
			OutputStream outStream = conn.getOutputStream();
			outStream.write(Util.int2bytes(len));
			outStream.write(data);
			outStream.flush();  
			outStream.close();  

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
			StringBuffer sb = new StringBuffer();  
			String line;
			while ((line = br.readLine()) != null) sb.append(line);
			Log.d("@Receive",sb.toString());
			return sb.toString(); 
 		}catch(Exception e){  
 			if (e!=null){
 				if (e.getMessage()!=null) Log.e("@Receive", e.getMessage());
 				else e.printStackTrace();
 			}
 			return null;
        }
	}
    
	public String doPostData(String basicURL, byte[] data,boolean reConnect){
		String str;
		if (postType==PostType.HTTPS){
			str=doSSLPostData(basicURL, data);
		}else{
			return ErrorCode.E_UNIMPLEMENTED.toString();
		}
		if (str==null){
			if (reConnect){
				for (int i=0;i<5;++i){
					Message msg = Message.obtain();
					msg.what = YJWMessage.NET_FAIL_RECONNECT.ordinal();
					msg.arg1=i;
					ShowMessageThread.mHandler.sendMessage(msg);
					try {
						Thread.sleep(NetworkFactory.ReconnectTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (postType==PostType.HTTPS) 
						str=doSSLPostData(basicURL, data);
					else 
						return ErrorCode.E_UNIMPLEMENTED.toString();
				}
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
			else {
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
		}
		return str;
	}
	
	
    public byte[] doSSLPostData(String basicURL, List<BasicNameValuePair> parameters){
    	String https = NetworkConstants.SERVER_URL_HTTPS+basicURL;//https　uri  
    	String query = "";
		boolean start=true;
		for (BasicNameValuePair pair:parameters){
			if (start){
				start=false;
			}else{
				query+="&";
			}
			query+=pair.getName()+"="+pair.getValue();				
		}
    	byte[] data = query.getBytes();
    	try{  
    		Log.d("@URL", https);
    		Log.d("@Parameters", query);
    		SSLContext sc = SSLContext.getInstance("TLS");  
    		sc.init(null, new TrustManager[]{new EasyTrustManager()}, new SecureRandom());  
    		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());  
    		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
    			@Override public boolean verify(String hostname, SSLSession session) {return true;}
    		});  
    		HttpsURLConnection conn = (HttpsURLConnection)new URL(https).openConnection();  
    		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");  
    		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
    		conn.setDoOutput(true);  
    		conn.setDoInput(true);  
    		conn.connect();  
    		
    		OutputStream outStream = conn.getOutputStream();
    		outStream.write(data);
    		outStream.flush();
    		outStream.close();
    		
    		InputStream is=conn.getInputStream();
    		byte[] b4=new byte[4];
    		is.read(b4);
    		byte[] bs=new byte[Util.bytes2int(b4)];
    		is.read(bs);
    		Log.d("@Receive","[data] length:"+bs.length);
    		return bs;
     		}catch(Exception e){  
     			Log.e("@Receive", e.getMessage());
            return null;
     	}	
    }
    
    public byte[] doPostData(String basicURL, List<BasicNameValuePair> parameters, boolean reConnect){
		byte[] str;
		if (postType==PostType.HTTPS){
			str=doSSLPostData(basicURL, parameters);
		}else{
			return ErrorCode.E_UNIMPLEMENTED.toString().getBytes();
		}
		if (str==null){
			if (reConnect){
				for (int i=0;i<5;++i){
					Message msg = Message.obtain();
					msg.what = YJWMessage.NET_FAIL_RECONNECT.ordinal();
					msg.arg1=i;
					ShowMessageThread.mHandler.sendMessage(msg);
					try {
						Thread.sleep(NetworkFactory.ReconnectTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (postType==PostType.HTTPS) 
						str=doSSLPostData(basicURL, parameters);
					else 
						return ErrorCode.E_UNIMPLEMENTED.toString().getBytes();
				}
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
			else {
				Message msg = Message.obtain();
				msg.what = YJWMessage.NET_FAIL_NORECONNECT.ordinal();
				ShowMessageThread.mHandler.sendMessage(msg);
			}
		}
		return str;
	}
}
