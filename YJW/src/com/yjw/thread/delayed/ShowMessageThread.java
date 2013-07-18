package com.yjw.thread.delayed;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.yjw.ctrl.YJWControler;
import com.yjw.net.NetworkFactory;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;

/**
 * 显示Toast消息<br/>
 * 不过只被NetworkFactory引用<br/>
 *  TODO 扩展为公用
 * */
public class ShowMessageThread extends YJWBaseThread {
	private static ShowMessageThread instance = null;
	public static Handler mHandler;
	public static Context currentContext = null;
	private static String ConnectErrorMessage = "网络连接失败，请稍后再试";
	private static String ReconnectMessage = "网络连接失败，稍后重新连接";
	
	public static synchronized ShowMessageThread GetInstance(){
		if(instance == null) 
			instance=YJWControler.Start(ShowMessageThread.class, null);
		return instance;
	}
	public static void SetCurrentContext(Context c){
		currentContext = c;
	}
	
	@Override
	public void run() {
		Log.i("Thread","ShowMessageThread run");
		Looper.prepare(); 
		mHandler = new Handler() { 
			private long time = 0;			//防止多个重连消息重复显示
			@Override
			public void handleMessage(Message msg) {
				//Log.d("Message", "Get");
				if (currentContext == null) return; 
				switch(YJWMessage.values()[msg.what]){
					case NET_FAIL_RECONNECT:
						try{
							long timenow =System.currentTimeMillis(); 
							if(time ==0||timenow-time>=NetworkFactory.ReconnectTime){
								Toast.makeText(currentContext, ReconnectMessage, Toast.LENGTH_SHORT).show();
								time = timenow;
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						//Log.d("Message", "Reconnect");
						break;
					case NET_FAIL_NORECONNECT:
						try{
							Toast.makeText(currentContext, ConnectErrorMessage, Toast.LENGTH_SHORT).show();
						}catch(Exception e){
							e.printStackTrace();
						}
						//Log.d("Message", "Reconnect");
						break;
				}
				// process incoming messages here 
				super.handleMessage(msg);
			} 
		}; 
		Looper.loop();
		Log.i("Thread","ShowMessageThread stop");
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String generateURL() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
