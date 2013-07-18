package com.yjw.ctrl;

import android.os.Message;

import com.yjw.activity.TestActivity;
import com.yjw.activity.YJWActivity;
import com.yjw.bean.GetInfoBean;
import com.yjw.thread.async.SyncDealThread;
import com.yjw.thread.async.SyncTransThread;
import com.yjw.thread.async.SyncUserThread;
import com.yjw.util.YJWMessage;

public class GetDealCtrl extends BaseCtrl{
	
	YJWMessage[] getKey() {
		YJWMessage[] s={YJWMessage.SYNC_TRANS_SUCCESS,YJWMessage.BUFFER_USER,YJWMessage.BUFFER_DEAL};
		return s;
	}
	
	@Override
	public boolean handleMessage(Message msg) {
		if (getActivity() instanceof TestActivity){
			((TestActivity)getActivity()).refresh(null);
		}
		return false;
	}

	public void syncTrans(int page){
		GetInfoBean bean = new GetInfoBean();
		bean.setId(YJWActivity.user.getId());
		bean.setPage(page);
		YJWControler.Start(SyncTransThread.class, bean);
	}
	
	public void syncDeal(int page){
		GetInfoBean bean = new GetInfoBean();
		bean.setId(YJWActivity.user.getId());
		bean.setPage(page);
		YJWControler.Start(SyncDealThread.class, bean);
	}
	
	public void syncUser(){
		YJWControler.Start(SyncUserThread.class, YJWActivity.user.getId());
	}

}
