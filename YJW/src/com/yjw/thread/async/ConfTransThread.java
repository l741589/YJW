package com.yjw.thread.async;

import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class ConfTransThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CONFTRANS;
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_CONF_TRANS_ERROR,YJWMessage.CONF_TRANS_FAILED,"验证失败");
		RegisterError(ErrorCode.E_NOT_ENOUGH_BALANCE,YJWMessage.CONF_TRANS_NOT_ENOUGH_BALANCE,"没有足够的余额");
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.CONF_TRANS_SUCCESS.ordinal();
		super.OnSuccess();
	}

}
