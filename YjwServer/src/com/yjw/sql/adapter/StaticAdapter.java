package com.yjw.sql.adapter;

public class StaticAdapter extends IfAdapter {
	private String s;
	
	public StaticAdapter(String con) {
		s=con;
	}

	@Override
	String condition() {
		// TODO Auto-generated method stub
		return s;
	}

}
