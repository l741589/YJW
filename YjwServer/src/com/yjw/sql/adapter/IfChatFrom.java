package com.yjw.sql.adapter;

public class IfChatFrom extends IfAdapter {
	
	Integer id;	
	public IfChatFrom(Integer id) {
		this.id = id;
	}

	@Override
	String condition() {
		if (id==null) return null;
		else return "from_id='"+id+"'";
	}

}
