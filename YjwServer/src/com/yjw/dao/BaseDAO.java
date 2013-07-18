package com.yjw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.yjw.bean.Bean;
import com.yjw.bean.GetInfoBean;
import com.yjw.sql.BaseSQL;
import com.yjw.util.TemplateGetter;

public abstract class BaseDAO {

	protected JdbcTemplate jdbcTemplate;
	protected BaseSQL sql;

	public BaseDAO(BaseSQL sql) {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = sql;
	}

	public int add(Bean bean) {
		try{
			final Bean BEAN=bean;
			KeyHolder keyHolder = new GeneratedKeyHolder();
			//sql.add(packer)
			int ret=jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection)	throws SQLException {
					 return connection.prepareStatement(sql.add(BEAN),Statement.RETURN_GENERATED_KEYS);
				}
			},keyHolder);
			if (ret==-1)return -1; else
			if (ret==0) return 0; else
			if (keyHolder!=null&&keyHolder.getKey()!=null)
				return keyHolder.getKey().intValue();
			else return ret;
		}catch(Exception e){
			return -1;
		}
	}
	
	public int update(Bean bean){
		try{
			return jdbcTemplate.update(sql.update(bean));
		}catch(Exception e){
			return -1;
		}
	}
	
	public boolean del(int id) {
		boolean flag = false;
		if (this.jdbcTemplate.update(this.sql.del(id)) != 0) {
			flag = true;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> sync(GetInfoBean bean) {
		String sqlstr=sql.sync(bean);
		System.out.println(sqlstr);
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sqlstr);
		List<Integer> ret=new ArrayList<Integer>();
		for (Map<String,Object> map:list){
			ret.add((Integer)map.get("id"));
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public Bean get(int id) {
		try{
		Map<String,Object> map = jdbcTemplate.queryForMap(sql.get(id));
		return Bean.Pack(map,getBeanClass());
		}catch(Exception e){
			return null;
		}
	}
	
	public abstract Class<? extends Bean> getBeanClass();

}
