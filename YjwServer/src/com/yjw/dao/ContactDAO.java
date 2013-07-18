package com.yjw.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yjw.bean.Bean;
import com.yjw.bean.ContactBean;
import com.yjw.bean.ContactsBean;
import com.yjw.bean.GetInfoBean;
import com.yjw.bean.UserBean;
import com.yjw.sql.ContactSQL;

public class ContactDAO extends BaseDAO {

	public ContactDAO() {
		super(new ContactSQL());
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return ContactBean.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Bean get(int id) {
		List<Map<String,Object>> list=jdbcTemplate.queryForList(sql.get(id));
		List<ContactBean> tl=new ArrayList<ContactBean>();
		for (Map<String,Object> map:list){
			ContactBean bean = Bean.Pack(map,ContactBean.class);
			tl.add(bean);
		}
		ContactBean[] beans=new ContactBean[tl.size()];
		tl.toArray(beans);
		ContactsBean ret=new ContactsBean();
		ret.setContacts(beans);
		return ret;
	}

	@Override
	public List<Integer> sync(GetInfoBean bean) {
		return null;
	}
	
	@Override
	public int add(Bean bean) {
		super.add(bean);
		return addBack(bean);
	}
	
	public int addBack(Bean _bean){
		UserDAO userDao=new UserDAO();
		ContactBean bean=(ContactBean)_bean;
		UserBean target=(UserBean)userDao.getByCellphone(bean.getCellphone());
		if (target==null) return -1;
		UserBean self=(UserBean)userDao.get(bean.getId());
		ContactBean back=new ContactBean();
		back.setCellphone(self.getCellphone());
		back.setId(target.getId());
		return super.add(back);	
	}
	
	@SuppressWarnings("unchecked")
	public int addAllBack(String cellphone){
		List<Map<String,Object>> list=jdbcTemplate.queryForList(((ContactSQL)sql).getByCellphone(cellphone));
		for (Map<String,Object> map:list) addBack(Bean.Pack(map,ContactBean.class));
		return 0;
	}
}
