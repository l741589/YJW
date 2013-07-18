/*
 * Created By Yiheng Tao
 * 
 * YJWBaseThread.java
 * 
 * This is the abstract and base class of the threads using in this application
 * There is a handler should used for sending message to activities
 * Each children class should override the run function and set its own Handler
 * call SendMessage when sending message
 */

package com.yjw.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yjw.bean.PieceBean;
import com.yjw.ctrl.ControlCode;
import com.yjw.net.NetworkFactory;
import com.yjw.util.Util;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public abstract class YJWBaseThread extends Thread {
	public enum Type{STRING,SEND_OBJECT,RECV_OBJECT};
	private Map<ErrorCode,ErrorCheckListener> map=new HashMap<ErrorCode,ErrorCheckListener>();
	private ErrorCheckListener nullerror;
	protected Handler handler;
	protected Message msg;
	protected String[] back;
	protected Object bean;
	protected Type type(){return Type.STRING;}
	
	protected boolean check(ErrorCode errorCode){
		ErrorCheckListener listener=map.get(errorCode);
		if (listener!=null){
			msg=Message.obtain();
			listener.OnError();
			if (msg.what!=0) sendMessage();
		}
		return errorCode==ErrorCode.E_SUCCESS;
	}
	protected void RegisterError(ErrorCode code,ErrorCheckListener listener){
		if (code == null) nullerror=listener;
		else map.put(code, listener);
	}
	
	protected void RegisterError(final ErrorCode code,final YJWMessage what,final String discription){
		RegisterError(code, new ErrorCheckListener() {
			@Override
			public void OnError() {
				msg.what=what.ordinal();
				msg.arg1= code==null?0:code.ordinal();
				msg.arg2=ControlCode.C_TOAST_SHORT.ordinal();
				msg.obj=discription;
			}
		});
	}
	
	public void setBean(Object bean){
		this.bean=bean;
	}
	/**
	 * Function Name : SetHandler
	 * @param : Handler
	 */
	public void setHandler(Handler h) {
		this.handler = h;
	}

	/**
	 * Function Name : SendMessage
	 * @param : Message
	 */
	protected void sendMessage() {
		assert msg!=null:"Message cannot be null";
		assert handler!=null:"Handler cannot be null";
		//*
		String log=YJWMessage.values()[msg.what]+","+msg.arg1+","+msg.arg2+",";
		if (msg.obj!=null) log+=msg.obj; else log+="null";
		Log.i("SendMessage",log);//*/
		
		if (handler!=null) handler.sendMessage(msg);
	}
	
	boolean check(Object obj){
		if (Util.isEmpty(obj)){
			Log.i("back","null");
			if (nullerror!=null){
				msg=Message.obtain();
				nullerror.OnError();
				if (msg.what!=0) sendMessage();				
			}
			return false;
		}
		return true;
	}
	
	void workWithBackStr(String backStr){
		back = backStr.split("&");
		if (check(ErrorCode.valueOf(back[0]))){
			msg=Message.obtain();
			msg.what=0;
			msg.obj=backStr;
			OnSuccess();
			if (msg.what!=0) sendMessage();
		}
	}
	
	@Override
	public void run() {
		Log.i("Thread",getClass().getSimpleName()+" run");
		init();
		switch (type()){
		case STRING:{
			String backStr=NetworkFactory.getInstance().doPost(generateURL(), generateParameters(),false);		
			if (check(backStr))	workWithBackStr(backStr);
		}break;
		case SEND_OBJECT:{
			String backStr=NetworkFactory.getInstance().doPostData(generateURL(), ((PieceBean)bean).toBytes(), false);
			if (check(backStr))	workWithBackStr(backStr);
		}break;
		case RECV_OBJECT:{
			byte[] backs=NetworkFactory.getInstance().doPostData(generateURL(),generateParameters(), false);
			if (check(backs)){
				msg=Message.obtain();
				msg.what=0;
				msg.obj=backs;
				OnSuccess();
				if (msg.what!=0) sendMessage();
			}
		}break;
		}
		Log.i("Thread",getClass().getSimpleName()+" stop");
	}
	
	/**
	 * Function Name : GenerateParameters
	 * @Abstract
	 */
	protected List<BasicNameValuePair> generateParameters(){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		if (bean!=null) params.add(new BasicNameValuePair("bean",bean.toString()));
		return params;
	}
	
	protected void OnSuccess(){
		msg.arg1 = ErrorCode.E_SUCCESS.ordinal();
	};
	
	protected abstract String generateURL();
	protected abstract void init();	
}
