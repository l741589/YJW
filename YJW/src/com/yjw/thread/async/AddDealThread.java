package com.yjw.thread.async;

import com.yjw.ctrl.ControlCode;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class AddDealThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_ADDDEAL;
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_ADD_DEAL_FAILED, YJWMessage.ADD_DEAL_FAILED,"添加业务失败");
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.ADD_DEAL_SUCCESS.ordinal();
		msg.obj="添加业务成功";
		msg.arg2=ControlCode.C_TOAST_SHORT.ordinal();
		super.OnSuccess();
	}

}
