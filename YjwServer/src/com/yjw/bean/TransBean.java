package com.yjw.bean;

import java.sql.Timestamp;

public class TransBean extends Bean {
	private Integer id;
	private Integer deal_id;
	private Integer from_id;
	private Integer to_id;
	private Integer times;
	private Timestamp timestamps;
	private Integer confirmed;
	
	public Integer getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(Integer deal_id) {
		this.deal_id = deal_id;
	}
	
	public Integer getFrom_id() {
		return from_id;
	}
	public void setFrom_id(Integer from_id) {
		this.from_id = from_id;
	}
	public Integer getTo_id() {
		return to_id;
	}
	public void setTo_id(Integer to_id) {
		this.to_id = to_id;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Timestamp getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Timestamp timestamps) {
		this.timestamps = timestamps;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(Integer confirmed) {
		this.confirmed = confirmed;
	}
	
}
