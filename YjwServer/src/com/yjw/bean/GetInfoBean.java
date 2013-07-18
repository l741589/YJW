package com.yjw.bean;

import java.sql.Timestamp;

public class GetInfoBean extends Bean {
	private int id;
	private Integer page;
	private Integer arg1;
	private Integer arg2;
	private Boolean esc;
	private Timestamp timestamps;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Timestamp getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Timestamp timestamps) {
		this.timestamps = timestamps;
	}
	public Integer getArg1() {
		return arg1;
	}
	public void setArg1(Integer arg1) {
		this.arg1 = arg1;
	}
	public Integer getArg2() {
		return arg2;
	}
	public void setArg2(Integer arg2) {
		this.arg2 = arg2;
	}
	public Boolean getEsc() {
		return esc;
	}
	public void setEsc(Boolean esc) {
		this.esc = esc;
	}

}
