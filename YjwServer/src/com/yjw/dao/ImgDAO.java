package com.yjw.dao;

import com.yjw.bean.Bean;
import com.yjw.bean.DataBean;
import com.yjw.bean.ImageBean;
import com.yjw.sql.ImgSQL;

public class ImgDAO extends BaseDAO {
	
	private DataDAO dataDao=new DataDAO();

	public ImgDAO() {
		super(new ImgSQL());
	}
	
	@Override
	public int add(Bean bean) {
		DataBean db=bean.to(DataBean.class);
		db.setMark("IMG");
		int did=dataDao.add(db);
		if (did<=0) return did;
		ImageBean ib=bean.to(ImageBean.class);
		ib.setDid(did);
		return super.add(ib);
	}
	
	@Override
	public Bean get(int id) {
		ImageBean bean=(ImageBean)super.get(id);
		if (bean==null) return null;
		int did=bean.getDid();
		DataBean db=(DataBean)dataDao.get(did);
		if (db==null) return null;
		bean.setData(db);
		return bean;
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return ImageBean.class;
	}
}
