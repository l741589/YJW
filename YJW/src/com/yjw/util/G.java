package com.yjw.util;

import java.util.Vector;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;
import com.yjw.database.DBProxy;
import com.yjw.database.DBStatic;
import com.yjw.thread.delayed.BufferThread;

/**缓冲的数据和全局变量*/
public class G {
	static public Vector<Integer> transes=new Vector<Integer>();
	static public Vector<Integer> deals=new Vector<Integer>();
	static public Vector<String> cellphones=new Vector<String>();
	
	public static void addTrans(TransBean bean){
		DBProxy.insertBean(bean, DBStatic.TransTableName);
	}
	
	public static void addUser(UserBean bean){
		DBProxy.insertBean(bean, DBStatic.UserTableName);
	}
	
	public static void addDeal(DealBean bean){
		DBProxy.insertBean(bean, DBStatic.DealTableName);
	}
	
	public static void addImage(Integer id,byte[] data){
		ContentValues cv=new ContentValues();
		cv.put("id", id);
		cv.put("data", data);
		DBProxy.insert(DBStatic.ImageTableName,cv);
		Log.i("DBProxy","inserted image");
	}
	
	public static TransBean getTrans(Integer id){		
		TransBean bean=DBProxy.selectBean(DBStatic.TransTableName, "id="+id, TransBean.class);
		if (bean==null) {
			BufferThread.addTrans(id);
		}
		return bean;
	}
	
	public static UserBean getUser(Integer id){
		UserBean bean=DBProxy.selectBean(DBStatic.UserTableName, "id="+id, UserBean.class);
		if (bean==null) {
			BufferThread.addUser(id);
		}
		return bean;
	}
	
	public static UserBean getUser(String cellphone){
		UserBean bean=DBProxy.selectBean(DBStatic.UserTableName, "cellphone="+cellphone, UserBean.class);
		/*if (bean==null) {
			//BufferThread.addUser(cellphone);
			return null;
		}*/
		return bean;
	}
	
	public static DealBean getDeal(Integer id){
		DealBean bean=DBProxy.selectBean(DBStatic.DealTableName, "id="+id, DealBean.class);
		if (bean==null){
			BufferThread.addDeal(id);
			return null;
		}
		return bean;
	}
	
	public static Drawable getImage(Integer id){
		byte[] bs=DBProxy.selectBlob(DBStatic.ImageTableName, id);
		if (bs==null) return null;
		Log.i("getImage","Image length:"+bs.length);
		Bitmap bmp=BitmapFactory.decodeByteArray(bs, 0, bs.length);
		return ImgTool.getInstance().bitmap2Drawable(bmp);
	}
	
	
	private static Boolean isRun = true;	
	public static Boolean getIsRun() {
		boolean ret=false;
		synchronized (isRun) {
			ret=isRun;
		}
		return ret;
	}
	
	public static void setIsRun(Boolean isRun) {
		synchronized (isRun) {
			G.isRun = isRun;		
		}		
	}
}
