package com.yjw.bean;

import java.sql.Timestamp;

public class DataBean extends Bean {
	
	private Integer id;
	private Integer length;
	private Integer done;
	private String mark;
	private Timestamp timestamps;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Timestamp timestamps) {
		this.timestamps = timestamps;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getDone() {
		return done;
	}
	public void setDone(Integer done) {
		this.done = done;
	}
}
