package com.yjw.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.yjw.util.Util;

public abstract class NetworkFactory {

	public static Type type(){return Type.STRING;}
	public static final String SERVER_ADDR = "http://localhost:8080/";
	public static final String APP_NAME = "YjwServer";
	
	public static String getURL(Tester tester){ return SERVER_ADDR+APP_NAME+"/"+tester.getAction(); }
			
	public static String BuildRequestString(Map<String,String> map){
		if (map==null) return "";
		boolean start = true;
		Set<String> keys = map.keySet();
		StringBuilder sb=new StringBuilder();
		for (String key:keys){
			if (start) start = false; else sb.append('&');
			sb.append(key);
			sb.append('=');
			sb.append(map.get(key));
		}
		return sb.toString();
	}
	
	public static String readResultString(InputStream is){
		try {
			String curLn="";
			String ret = "";
			curLn = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			while ((curLn = reader.readLine()) != null) {
				ret += curLn;
			}
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String doPost(Tester tester){
		try {
			Map<String,String> params=new HashMap<String, String>();
			tester.post(params);
			URLConnection connection=new URL(getURL(tester)).openConnection();
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			OutputStreamWriter outStream = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			outStream.write(BuildRequestString(params));
			outStream.flush();
			outStream.close();
			return readResultString(connection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String doPostDataSend(Tester tester){
		try {
			ByteArrayOutputStream stream=new ByteArrayOutputStream();
			tester.postData(stream);
			byte[] data=stream.toByteArray(); 
			URLConnection connection=new URL(getURL(tester)).openConnection();
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			int len=data.length;
			OutputStream outStream = connection.getOutputStream();
			outStream.write(Util.int2bytes(len));
			outStream.write(data);
			outStream.flush();  
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static byte[] doPostDataRecv(Tester tester){
		try {
			Map<String,String> params=new HashMap<String, String>();
			tester.post(params);
			URLConnection connection=new URL(getURL(tester)+"?"+BuildRequestString(params)).openConnection();
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			OutputStreamWriter outStream = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			outStream.write(BuildRequestString(params));
			outStream.flush();
    		outStream.close();
    		
    		InputStream is=connection.getInputStream();
    		byte[] b4=new byte[4];
    		is.read(b4);
    		byte[] bs=new byte[Util.bytes2int(b4)];
    		is.read(bs);
    		return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String doGet(Tester tester){
		URLConnection connection;
		try {
			Map<String,String> params=new HashMap<String, String>();
			tester.post(params);
			ByteArrayOutputStream bs=new ByteArrayOutputStream();
			OutputStreamWriter writer=new OutputStreamWriter(bs,"UTF-8");
			writer.append(getURL(tester)+"?"+BuildRequestString(params));
			writer.close();
			bs.close();
			String url=bs.toString();
			System.out.println(getURL(tester)+"?"+BuildRequestString(params));
			connection =new URL(url).openConnection();
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.connect();
			return readResultString(connection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
