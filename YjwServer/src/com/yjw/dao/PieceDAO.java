package com.yjw.dao;

import java.io.ByteArrayInputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.yjw.bean.Bean;
import com.yjw.bean.DataBean;
import com.yjw.bean.PieceBean;
import com.yjw.sql.PieceSQL;
import com.yjw.util.TemplateGetter;

public class PieceDAO extends BaseDAO {
	
	private DataDAO dataDao=new DataDAO();

	public PieceDAO() {
		super(new PieceSQL());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return null;
	}
	
	@Override
	public int add(Bean _bean) {
		final PieceBean bean=(PieceBean)_bean;
		try{
			final int id=bean.getId();
			final int pid=bean.getPid();
			final byte[] data=bean.getData();
			TransactionTemplate tt=TemplateGetter.getTtl();
			return (Integer)tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus arg0) {
					DataBean db=(DataBean)dataDao.get(id);
					db.setDone(db.getDone()+data.length);
					dataDao.update(db);
					return (Integer)jdbcTemplate.execute(sql.add(bean), new CallableStatementCallback() {			
						public Object doInCallableStatement(CallableStatement cs)
								throws SQLException, DataAccessException {
								cs.setInt(1,id);
								cs.setInt(2,pid);
								cs.setBlob(3,new ByteArrayInputStream(data));
								return cs.executeUpdate();
						}
					});
				}
			});
			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public Bean get(int id,int pid) {
		try{
			Map<String,?> map=jdbcTemplate.queryForMap(((PieceSQL)sql).get(id, pid));
			PieceBean bean=Bean.Pack(map,PieceBean.class);
			bean.setData((byte[])map.get("data"));
			return bean;
		}catch(Exception e){
			return null;
		}
	}
}
