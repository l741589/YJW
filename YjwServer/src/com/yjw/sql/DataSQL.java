package com.yjw.sql;

import com.yjw.bean.GetInfoBean;

public class DataSQL extends BaseSQL {

	@Override
	public String Table() {
		return "yjw_data";
	}

	@Override
	public String sync(GetInfoBean bean) {
		return null;
	}

}
