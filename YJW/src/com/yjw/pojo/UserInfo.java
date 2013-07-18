package com.yjw.pojo;

import java.io.Serializable;

public class UserInfo extends BaseUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4282150840363851387L;
	protected String username;
	protected String password;
	protected String sid;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
