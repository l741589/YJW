package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.adapter.IfId;

public class CtrlSQL extends BaseSQL {
	
	@Override
	public String fields() {
		return "code";
	}

	@Override
	public String Table() {
		return "yjw_ctrl";
	}

	@Override
	public String sync(GetInfoBean bean) {
		return sync(bean,new IfId(bean.getId()));
	}

}
