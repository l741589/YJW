package com.yjw.sql;

import com.yjw.bean.Bean;
import com.yjw.bean.GetInfoBean;
import com.yjw.sql.adapter.IfAdapter;
import com.yjw.sql.adapter.Order;

public abstract class BaseSQL {
	public final int PAGE_SIZE = 20;
	public abstract String Table();
	public abstract String sync(GetInfoBean bean);
	public String sync(GetInfoBean bean,String fields){return null;}
	public String fields(){return "*";}
	
	public String log(String s){
		System.out.println(s);
		return s;
	}
	
	public String add(Bean bean){
		return log(bean.insert(Table()));
	}
	public String update(Bean bean){
		return log(bean.update(Table()));
	}
	public String get(int id){
		return log("SELECT "+fields()+" FROM "+Table()+" WHERE id='"+id+"'");		
	}
	public String del(int id){
		return log("DELETE FROM "+Table()+" WHERE id='"+id+"'"); 
	}
	public String sync(GetInfoBean bean,IfAdapter condition){
		return log("SELECT id FROM "+Table()+" WHERE "+condition+
				new Order(bean.getPage(),PAGE_SIZE,bean.getEsc()));
	}	
	
}
