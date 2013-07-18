package com.yjw.thread.delayed;

import com.yjw.ctrl.ControlCode;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;

public class ControlThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CONTROL;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.CONTROL_RECV.ordinal();
		msg.arg2=ControlCode.C_TOAST_SHORT.ordinal();
		msg.obj="œÏ”¶≥…π¶";
		super.OnSuccess();
	}

}
