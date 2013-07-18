package com.yjw.thread.async;

import com.yjw.bean.Bean;
import com.yjw.bean.ContactBean;
import com.yjw.bean.ContactsBean;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.thread.delayed.BufferThread;
import com.yjw.util.G;
import com.yjw.util.YJWMessage;

public class SyncUserThread extends YJWBaseThread {

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_SYNCUSER;
	}

	@Override
	protected void init() {}
	
	@Override
	protected void OnSuccess() {
		ContactsBean cbs=Bean.Pack(back[1],ContactsBean.class);
		ContactBean[] beans=cbs.getContacts();
		if (beans!=null){
			for (ContactBean bean:beans){
				synchronized (G.cellphones) {
					if (!G.cellphones.contains(bean.getCellphone())){
						G.cellphones.add(bean.getCellphone());
					}
				}
				BufferThread.addUser(bean.getCellphone());
			}
		}
		msg.what=YJWMessage.SYNC_USER_SUCCESS.ordinal();
		super.OnSuccess();
	}

}
