package com.yjw.pojo;

import java.io.Serializable;

public class BaseUserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8754374113514460499L;

	protected String id;
	protected String phoneNumber;
	protected String realName;

	public BaseUserInfo(String id, String phone, String name) {
		this.id = id;
		this.phoneNumber = phone;
		this.realName = name;
	}

	public BaseUserInfo() {

	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
