package com.yjw.sql.adapter;

public class IfChatTo extends IfAdapter {

	Integer id;	
	public IfChatTo(Integer id) {
		this.id = id;
	}

	@Override
	String condition() {
		if (id==null) return null;
		else return "to_id='"+id+"'";
	}

}
