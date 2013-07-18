package com.yjw.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yjw.bean.ArrayBean;
import com.yjw.bean.Bean;
import com.yjw.sql.CtrlSQL;

public class CtrlDAO extends BaseDAO {
	
	

	public CtrlDAO() {
		super(new CtrlSQL());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return ArrayBean.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Bean get(int id) {
		try{
			List<Map<String,Object>> list = jdbcTemplate.queryForList(sql.get(id));
			List<Integer> is=new ArrayList<Integer>();
			for (Map<String,Object> map:list){
				int x=(Integer)map.get("code");
				is.add(x);
			}
			Integer[] ari=new Integer[is.size()];
			is.toArray(ari);
			ArrayBean<Integer> bean = new ArrayBean<Integer>();
			bean.setData(ari);
			return bean;
		}catch(Exception e){
			return null;
		}
	}

}
