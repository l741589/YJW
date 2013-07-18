package com.yjw.thread.async;

import com.yjw.bean.Bean;
import com.yjw.ctrl.ControlCode;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;

public class GetImageThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_GETIMAGE;
	}
	
	@Override
	protected void init() {
	}
	
	@Override
	protected void OnSuccess() {
		super.OnSuccess();
		msg.what=YJWMessage.GET_IMAGE_SUCCESS.ordinal();
		msg.arg2=ControlCode.C_ADD_IMAGE.ordinal();
		msg.obj=Bean.Pack(back[1]);
	}

}
