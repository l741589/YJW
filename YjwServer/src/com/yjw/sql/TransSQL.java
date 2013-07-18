package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.adapter.IfTransFrom;
import com.yjw.sql.adapter.IfTransOwner;
import com.yjw.sql.adapter.IfTransTo;
import com.yjw.sql.adapter.OrAdapter;

public class TransSQL extends BaseSQL{
	
	public String sync(GetInfoBean bean){
		return sync(bean,new OrAdapter(
				new IfTransFrom(bean.getId()),
				new IfTransTo(bean.getId(), true),
				new IfTransOwner(bean.getId(),false)
		));
	}
	
	public String confirm(int id){
		return "update yjw_trans set confirmed='1' where id='"+id+"'";
	}

	@Override
	public String Table() {
		return "yjw_trans";
	}

}
