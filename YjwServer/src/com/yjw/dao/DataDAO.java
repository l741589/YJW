package com.yjw.dao;

import com.yjw.bean.Bean;
import com.yjw.bean.DataBean;
import com.yjw.sql.DataSQL;

public class DataDAO extends BaseDAO {

	public DataDAO() {
		super(new DataSQL());
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return DataBean.class;
	}

}
