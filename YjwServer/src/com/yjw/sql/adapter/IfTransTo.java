package com.yjw.sql.adapter;

public class IfTransTo extends IfAdapter {
	
	int to_id;
	Boolean conf=null;
	
	public IfTransTo(int id) {
		to_id=id;
	}
	
	public IfTransTo(int id,boolean confirmed) {
		to_id=id;
		conf=confirmed;
	}

	@Override
	String condition() {
		if (conf==null){
			return "to_id='"+to_id+"'";
		}else{
			int b;
			if (conf) b=1; else b=0;
			return new AndAdapter(
					new IfTransTo(to_id),
					new StaticAdapter("confirmed='"+b+"'")
				).toString();			
		}
	}

}
