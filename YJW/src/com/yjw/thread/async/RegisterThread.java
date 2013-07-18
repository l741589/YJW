package com.yjw.thread.async;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class RegisterThread extends YJWBaseThread {

	public enum RegisterStep {getValidateCode, Register};
	
	private RegisterStep currentStep = RegisterStep.getValidateCode;
	private String cellphone;
	
	public void setCellphone(String cellphone){
		this.cellphone=cellphone;
	}
	public void setStep(RegisterStep currentStep){
		this.currentStep=currentStep;
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = null;
		switch (currentStep) {
		case getValidateCode:
			parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("cellphone", cellphone));
			break;			
		case Register:
			parameters=super.generateParameters();
			break;
		}
		return parameters;
	}

	@Override
	protected String generateURL() {
		String url = null;
		switch (currentStep) {
		case getValidateCode:url = NetworkConstants.URL_VALIDATECODE;break;
		case Register:	url = NetworkConstants.URL_REGISTER;	break;
		}
		return url;
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_DUBLICATE_ID, YJWMessage.REGISTER_FAILURE, "已经注册的账号");
		RegisterError(ErrorCode.E_DUBLICATE_NAME, YJWMessage.REGISTER_FAILURE, "已经注册的昵称");
		RegisterError(ErrorCode.E_INVALIDATE_FAILED, YJWMessage.REGISTER_FAILURE, "验证失败");
	}

	@Override
	protected void OnSuccess() {
		switch(currentStep){
		case getValidateCode:{
			msg.what = YJWMessage.REGISTER_GETVALIDATECODE.ordinal();
			msg.obj=back[1];
		}break;
		case Register:{
			msg.what = YJWMessage.REGISTER_SUCCESS.ordinal();
		}break;		
		}
		super.OnSuccess();
	}
}
