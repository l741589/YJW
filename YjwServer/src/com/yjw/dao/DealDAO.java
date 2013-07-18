package com.yjw.dao;

import com.yjw.bean.Bean;
import com.yjw.bean.DealBean;
import com.yjw.sql.DealSQL;

/*关于交易的具体实现封装*/
public class DealDAO extends BaseDAO{

	public DealDAO() {
		super(new DealSQL());
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return DealBean.class;
	}	
}
