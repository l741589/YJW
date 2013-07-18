package com.yjw.sql.adapter;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.DealSQL;

public class IfTransOwner extends IfAdapter {
	
	int owner_id;
	Boolean conf=null;
	
	public IfTransOwner(int id) {
		owner_id=id;
	}
	
	public IfTransOwner(int id,boolean confirmed) {
		owner_id=id;
		conf=confirmed;
	}

	@Override
	String condition() {
		if (conf==null){
			DealSQL dealsql=new DealSQL();
			GetInfoBean gi=new GetInfoBean();
			gi.setId(owner_id);
			String ret=dealsql.sync(gi);
			ret="deal_id IN ("+ret+")";
			return ret;
		}else{
			int b;
			if (conf) b=1; else b=0;
			return new AndAdapter(
					new IfTransOwner(owner_id),
					new StaticAdapter("confirmed='"+b+"'")
				).toString();			
		}
	}

}
