package com.yjw.thread.async;

import com.yjw.bean.AddTransBackLogBean;
import com.yjw.bean.Bean;
import com.yjw.bean.UserBean;
import com.yjw.ctrl.ControlCode;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.ErrorCheckListener;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class AddTransThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_ADDTRANS;
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_ADD_TRANS_FAILED, new ErrorCheckListener() {
			@Override
			public void OnError() {
				AddTransBackLogBean bean = Bean.Pack(back[1],AddTransBackLogBean.class);
				msg.what=YJWMessage.GET_TRANS_FAILED.ordinal();
				msg.arg1=ErrorCode.E_ADD_TRANS_FAILED.ordinal();
				String s="";
				if (bean.getNot_reg()!=null){
					s+="以下用户没有注册\n";
					for (UserBean u:bean.getNot_reg()){
						s+=u.getCellphone()+"\n";
					}
				}
				if (bean.getFailed()!=null){
					s+="以下用户已注册但转介失败\n";
					for (UserBean u:bean.getFailed()){
						s+=u.getCellphone()+"\n";
					}
				}
				if (bean.getOvertrans()!=null){
					s="超出最大转介次数";
				}
				s=s.substring(0, s.length()-1);
				msg.arg2=ControlCode.C_TOAST_LONG.ordinal();
				msg.obj=s;
			}
		});
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.GET_TRANS_SUCCESS.ordinal();
		super.OnSuccess();
	}

}
