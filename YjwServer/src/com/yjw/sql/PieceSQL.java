package com.yjw.sql;

import com.yjw.bean.Bean;
import com.yjw.bean.GetInfoBean;

public class PieceSQL extends BaseSQL {

	@Override
	public String Table() {
		return "yjw_piece";
	}

	@Override
	public String sync(GetInfoBean bean) {
		return null;
	}
	
	@Override
	public String add(Bean bean) {
		return log("INSERT INTO "+Table()+"(id,pid,data) VALUES(?,?,?)");
	}
	
	public String get(int id, int pid) {
		return log("SELECT * FROM "+Table()+" WHERE id='"+id+"' AND pid='"+pid+"'");
	} 

}
