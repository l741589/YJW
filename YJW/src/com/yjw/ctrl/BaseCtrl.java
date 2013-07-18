package com.yjw.ctrl;

import android.app.Activity;
import android.os.Handler.Callback;

import com.yjw.util.YJWMessage;

public abstract class BaseCtrl implements Callback {

	abstract YJWMessage[] getKey();
	public BaseCtrl() {
		super();
		YJWMessage[] keys=getKey();
		for (YJWMessage msg:keys){
			YJWControler.registerCallback(msg, this);
		}
	}
	public Activity getActivity() {
		return YJWControler.getInstance().getActivity();
	}
}
