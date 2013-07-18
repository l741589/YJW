package com.yjw.thread.async;

import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class TemplateThread extends YJWBaseThread {
	
	public YJWMessage successmessage;
	public YJWMessage errormessage;
	public ErrorCode errorCode;
	public String errorStr;
	public String url;

	@Override
	protected String generateURL() {
		return url;
	}

	@Override
	protected void init() {
		if (errorCode!=null&&errormessage!=null&&errorStr!=null)
			RegisterError(errorCode, errormessage,errorStr);
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=successmessage.ordinal();
		super.OnSuccess();
	}

}
