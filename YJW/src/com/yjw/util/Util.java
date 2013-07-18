package com.yjw.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;

import com.yjw.util.shared.SharedUtil;

/**用于切换Activity, *检测更新, *更新 */
public class Util extends SharedUtil {

	public static final boolean TEST_MODE = false;

	public static void startNewActivity(Activity current, Class<?> cls,	boolean finishSelf) {
		Intent intent = new Intent(current, cls);
		current.startActivity(intent);
		if (finishSelf) current.finish();
	}
	
	@SuppressWarnings("unchecked")
	public static<T> T[] list2Arr(List<Object> list,Class<T> type){
		T[] ret=(T[])Array.newInstance(type,list.size());
		list.toArray(ret);	
		return ret;		
	}
			
	public static<T> List<T> arr2List(T[] array){
		List<T> ret=new ArrayList<T>();
		for (T e:array){
			ret.add(e);
		}
		return ret;
	}
	
	
}
