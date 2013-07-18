package com.yjw.dao;

import java.util.Map;

import com.yjw.bean.Bean;
import com.yjw.bean.UserBean;
import com.yjw.sql.UserSQL;

public class UserDAO extends BaseDAO {
	public UserDAO() {
		super(new UserSQL());
	}
	@Override
	public Class<? extends Bean> getBeanClass() {
		return UserBean.class;
	}
	@SuppressWarnings("unchecked")
	public Bean getByCellphone(String cellphone) {
		try{
			Map<String,?> map = jdbcTemplate.queryForMap(((UserSQL)sql).getByCellphone(cellphone));
			return Bean.Pack(map,UserBean.class);
		}catch(Exception e){
				return null;
		}
	}

	

/*	public BeanPacker getUser(int id) {
		try{
			Map<String,?> map=jdbcTemplate.queryForMap(sql.getUser(id));
			return new BeanPacker(map,UserBean.class);
		}catch(Exception e){
			return null;
		}
	}*/

}
