package com.yjw.sql;

import com.yjw.bean.GetInfoBean;

public class UserSQL extends BaseSQL{
	
	@Override
	public String fields() {
		return "id,name,cellphone,email,timestamps,balance";
	}

	@Override
	public String Table() {
		return "yjw_user";
	}

	@Override
	public String sync(GetInfoBean bean) {
		return null;
	}

	public String getByCellphone(String cellphone) {
		return log("SELECT "+fields()+" FROM "+Table()+" WHERE cellphone='"+cellphone+"'");
	}
	
	

	
}
