package com.yjw.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class DealBean extends Bean {
	private Integer id;
	private Integer owner_id;
	private String title;
	private String content;
	private Float fee;
	private Float commission;
	private Date expire_date;
	private Timestamp timestamps;
	private Integer maxtrans;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Float getFee() {
		return fee;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}
	
	public Date getexpire_date() {
		return expire_date;
	}

	public void setexpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}

	public Timestamp getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(Timestamp timestamps) {
		this.timestamps = timestamps;
	}

	public Integer getMaxtrans() {
		return maxtrans;
	}

	public void setMaxtrans(Integer maxtrans) {
		this.maxtrans = maxtrans;
	}

	public Integer getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(Integer owner_id) {
		this.owner_id = owner_id;
	}

}
