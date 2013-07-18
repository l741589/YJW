package com.yjw.sql.adapter;

public class IfId extends IfAdapter {

	int id;
	public IfId(int id) {
		this.id=id;
	}
	@Override
	String condition() {
		return "id='"+id+"'";
	}

}
