package com.yjw.dao;

import com.yjw.bean.Bean;
import com.yjw.bean.UserBean;
import com.yjw.sql.ChatSQL;

public class ChatDAO extends BaseDAO{

	public ChatDAO() {
		super(new ChatSQL());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<? extends Bean> getBeanClass() {
		return UserBean.class;
	}

	
}
