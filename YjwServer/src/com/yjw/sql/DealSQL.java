package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.adapter.IfDealOwner;


public class DealSQL extends BaseSQL{
	
	public String sync(GetInfoBean bean) {
		return sync(bean,new IfDealOwner(bean.getId()));
	}	
	
	@Override
	public String Table() {		
		return "yjw_deal";
	}
}
