package com.yjw.bean;

import com.yjw.util.Util;

public class UserBean extends Bean {
	private Integer id;
	private String name;
	private String cellphone;
	private String password;
	private String email;
	private String timestamps;
	private Float balance; 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (Util.isEmpty(email)) this.email=null;
		else this.email = email;
	}

	public String getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(String timestamps) {
		this.timestamps = timestamps;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}
}
