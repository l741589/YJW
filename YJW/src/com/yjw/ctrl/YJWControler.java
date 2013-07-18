package com.yjw.ctrl;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.widget.Toast;

import com.yjw.activity.BaseActivity;
import com.yjw.activity.YJWActivity;
import com.yjw.thread.YJWBaseThread;
import com.yjw.thread.async.InitTableThread;
import com.yjw.thread.async.TemplateThread;
import com.yjw.thread.delayed.ControlThread;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class YJWControler {
	
	private static YJWControler instance = null;
	private static Handler handler;
	private Activity activity;
	private YJWBaseThread thread;
	private Map<YJWMessage,Callback> map;
	private static YJWBaseThread controlThread;
	
	private void startControlThread(){
		if (controlThread==null||controlThread.isAlive()==false)
			if (YJWActivity.user!=null&&YJWActivity.user.getId()!=null)
				controlThread=start(ControlThread.class, YJWActivity.user.getId());

	}
	
	public static Handler getHandler(){
		return handler;
	}
	public Activity getActivity() {
		return activity;
	}
	public static Activity GetActivity() {
		return getInstance().getActivity();
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
		startControlThread();
	}
	public static void SetActivity(Activity activity) {
		getInstance().setActivity(activity);
	}
	
	public static YJWControler getInstance(){
		if (instance==null) instance = new YJWControler();
		return instance;
	}
	
	public YJWControler() {
		handler = new Handler(callback);
		map = new HashMap<YJWMessage,Callback>();
		startControlThread();
		start(InitTableThread.class, null);
	}
	
	private Callback callback = new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.obj==null||activity==null)	return false;
			callback=map.get(YJWMessage.values()[msg.what]);
			if (callback==null) return true;
			callback.handleMessage(msg);
			switch(ControlCode.values()[msg.arg2]){
			case C_TOAST_SHORT:Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_SHORT).show();break;
			case C_TOAST_LONG :Toast.makeText(activity, msg.obj.toString(), Toast.LENGTH_LONG ).show();break;
			case C_REFRESH:if (getActivity()!=null&&getActivity() instanceof BaseActivity)((BaseActivity)getActivity()).refresh(msg);break;
			}
			
			return true;
		}
	};
	
	public static void registerCallback(YJWMessage msg,Callback callback){
		getInstance().map.put(msg, callback);
	}
	public static void unregisterCallback(YJWMessage msg){
		getInstance().map.remove(msg);
	}
	
	public<T extends YJWBaseThread> T start(Class<T> type, Object bean){
		try {
			thread=type.newInstance();
			thread.setBean(bean);
			thread.setHandler(handler);
			//Log.d("Thread",thread+" ready");
			thread.start();
		} catch (InstantiationException e) {
			e.printStackTrace();
			thread = null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			thread = null;
		}
		//Log.d("Thread",thread+" run");
		return (T)thread;
	}
	
	public<T extends YJWBaseThread> T run(Class<T> type, Object bean){
		try {
			thread=type.newInstance();
			thread.setBean(bean);
			thread.setHandler(handler);
			//Log.d("Thread",thread+" ready");
			thread.run();
		} catch (InstantiationException e) {
			e.printStackTrace();
			thread = null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			thread = null;
		}
		//Log.d("Thread",thread+" run");
		return (T)thread;
	}
	
	public TemplateThread startTemplate(String url,Object bean,YJWMessage msgSuccess,YJWMessage msgError,ErrorCode error,String toast){
		thread=new TemplateThread();
		thread.setBean(bean);
		thread.setHandler(handler);
		((TemplateThread)thread).url=url;
		((TemplateThread)thread).successmessage=msgSuccess;
		((TemplateThread)thread).errormessage=msgError;
		((TemplateThread)thread).errorCode=error;
		((TemplateThread)thread).errorStr=toast;
		new Thread(thread).start();
		return (TemplateThread)thread;
	}
	
	public TemplateThread startTemplate(String url,Object bean,YJWMessage msgSuccess){
		return startTemplate(url, bean, msgSuccess,null,null,null);
	}
	
	public static TemplateThread StartTemplate(String url,Object bean,YJWMessage msgSuccess,YJWMessage msgError,ErrorCode error,String toast){
		return getInstance().startTemplate(url, bean, msgSuccess, msgError, error, toast);
	}
	
	public static TemplateThread StartTemplate(String url,Object bean,YJWMessage msgSuccess){
		return getInstance().startTemplate(url, bean, msgSuccess);
	}
		
	public static<T extends YJWBaseThread> T Start(Class<T> type, Object bean){
		return getInstance().start(type, bean);
	}
	
	public static<T extends YJWBaseThread> T Run(Class<T> type, Object bean){
		return getInstance().run(type, bean);
	}
	
}
