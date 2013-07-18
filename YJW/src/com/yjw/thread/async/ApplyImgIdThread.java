package com.yjw.thread.async;

import java.util.ArrayList;
import java.util.List;

import com.yjw.bean.Bean;
import com.yjw.bean.ImageBean;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class ApplyImgIdThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_APPLYIMGID;
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_APPLY_IMG_ID_FAILED,YJWMessage.APPLY_IMG_ID_FAILED, "添加业务失败");
		RegisterError(null, YJWMessage.APPLY_IMG_ID_FAILED, "添加业务失败");
	}
	
	@Override
	protected void OnSuccess() {
		msg.what=YJWMessage.APPLY_IMG_ID_SUCCESS.ordinal();
		List<ImageBean> list=new ArrayList<ImageBean>();
		for (int i=1;i<back.length;++i) list.add(Bean.Pack(back[i],ImageBean.class));
		msg.obj=list;
		super.OnSuccess();
	}
}
