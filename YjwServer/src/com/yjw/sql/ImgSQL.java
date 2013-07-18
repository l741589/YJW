package com.yjw.sql;

import com.yjw.bean.GetInfoBean;

public class ImgSQL extends BaseSQL {

	@Override
	public String Table() {
		return "yjw_img";
	}

	@Override
	public String sync(GetInfoBean bean) {
		return null;
	}
	
	public String set(){
		return log("UPDATE "+Table()+" SET data=? where id=?");
	}

}
