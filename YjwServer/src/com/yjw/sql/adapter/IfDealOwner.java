package com.yjw.sql.adapter;

public class IfDealOwner extends IfAdapter {
	
	int owner_id;
	
	public IfDealOwner(int id) {
		owner_id=id;
	}

	@Override
	String condition() {
		return "owner_id='"+owner_id+"'";
	}

}
