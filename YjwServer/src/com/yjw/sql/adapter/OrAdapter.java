package com.yjw.sql.adapter;

public class OrAdapter extends IfAdapter {

	private IfAdapter[] adapters;
	public OrAdapter(IfAdapter...adapters) {
		this.adapters=adapters;
	}

	@Override
	String condition() {
		String s="";
		boolean start=true;
		for (IfAdapter adapter:adapters){
			String ss=adapter.toString();
			if (ss==null) continue;
			if (start){
				s+="("; 
				start=false;
			}
			else s+=" OR ";
			s+=ss;
		}
		s+=")";
		if (s.equals("()")) return null;
		return s;
	}

}
