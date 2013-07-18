package com.yjw.sql;

import com.yjw.bean.GetInfoBean;

public class ContactSQL extends BaseSQL {

	@Override
	public String Table() {
		return "yjw_contact";
	}

	@Override
	public String sync(GetInfoBean bean) {
		return get(bean.getId());
	}
	
	public String getByCellphone(String cellphone){
		return log("SELECT "+fields()+" FROM "+Table()+" WHERE cellphone='"+cellphone+"'");
	}

}
