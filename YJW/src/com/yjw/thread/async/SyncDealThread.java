package com.yjw.thread.async;

import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.G;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.ErrorCode;

public class SyncDealThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_SYNCDEAL;
	}
	
	@Override
	protected void OnSuccess() {
		synchronized (G.deals) {
		G.deals.clear();
		msg.what=YJWMessage.SYNC_TRANS_SUCCESS.ordinal();
		for (int i=1;i<back.length;++i){
			Integer x = Integer.valueOf(back[i]);
			G.deals.add(x);			
		}
		}
		super.OnSuccess();
	}

	@Override
	protected void init() {
		RegisterError(ErrorCode.E_NULL_DEAL, YJWMessage.GET_DEAL_FAILED, "您还没有有关您的业务");

	}

}
