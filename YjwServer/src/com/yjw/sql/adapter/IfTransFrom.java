package com.yjw.sql.adapter;

public class IfTransFrom extends IfAdapter {
	
	int from_id;
	Boolean conf=null;
	
	public IfTransFrom(int id) {
		from_id=id;
	}
	
	public IfTransFrom(int id,boolean confirmed) {
		from_id=id;
		conf=confirmed;
	}

	@Override
	String condition() {
		if (conf==null){
			return "from_id='"+from_id+"'";
		}else{
			int b;
			if (conf) b=1; else b=0;
			return new AndAdapter(
					new IfTransFrom(from_id),
					new StaticAdapter("confirmed='"+b+"'")
				).toString();			
		}
	}

}
