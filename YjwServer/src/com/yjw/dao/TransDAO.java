package com.yjw.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.yjw.bean.Bean;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;
import com.yjw.sql.TransSQL;
import com.yjw.util.TemplateGetter;

public class TransDAO extends BaseDAO {
	
	public TransDAO() {
		super(new TransSQL());
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return TransBean.class;
	}
	
	public int confirm(final int id){
		try{
			TransactionTemplate tt=TemplateGetter.getTtl();
			return (Integer)tt.execute(new TransactionCallback() {
				public Object doInTransaction(TransactionStatus ts) {
					JdbcTemplate jt=TemplateGetter.getJtl();
					BaseDAO dealDao=new DealDAO();
					UserDAO userDao=new UserDAO();
					jt.update(((TransSQL)sql).confirm(id));
					TransBean tb=(TransBean)get(id);
					DealBean db=(DealBean)dealDao.get(tb.getDeal_id());
					UserBean fromb=(UserBean)userDao.get(db.getOwner_id());
					UserBean tob=(UserBean)userDao.get(tb.getFrom_id());
					if (fromb.getBalance()<db.getFee()) {
						ts.setRollbackOnly(); 
						return 1;
					}
					fromb.setBalance(fromb.getBalance()-db.getFee());
					tob.setBalance(tob.getBalance()+db.getFee());
					userDao.update(fromb);
					userDao.update(tob);
					return 0;			
				}
			}); 
		}catch(Exception e){
			return -1;
		}
	}
}
