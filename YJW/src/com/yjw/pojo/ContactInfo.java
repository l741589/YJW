package com.yjw.pojo;

import java.io.Serializable;

public class ContactInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1452744679073164118L;
	protected String name;
	protected String phone;
	protected String id;
	protected boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
