package com.yjw.bean;

import java.sql.Timestamp;

public class ImageBean extends Bean {
	
	private Integer id;
	private Integer did;
	private String mark;
	private Timestamp timestamps;
	private DataBean data;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Timestamp getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Timestamp timestamps) {
		this.timestamps = timestamps;
	}
	public DataBean getData() {
		return data;
	}
	public void setData(DataBean data) {
		this.data = data;
	}

}
