package com.yjw.sql.adapter;


public class Order {
	
	Integer pagesize;
	Integer page;
	String order;
	
	public Order(Integer page,Integer pagesize,Boolean esc) {
		this.pagesize=pagesize;
		this.page=page;
		if (esc==null||!esc){
			order=" DESC";
		}else order="";
	}
	
	@Override
	public String toString() {
		if (pagesize!=null&&page!=null){
			String ret=" ORDER BY timestamps"+order;		
			ret+=" LIMIT "+ page*pagesize + "," + pagesize;
			return ret;
		}return "";
	}

}
