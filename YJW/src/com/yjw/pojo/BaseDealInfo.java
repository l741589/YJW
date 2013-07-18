package com.yjw.pojo;

import java.io.Serializable;
import java.util.Date;

public class BaseDealInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1748487269982033802L;
	protected String id;
	protected String title;
	protected Date date;
	protected String creator;
	protected String referFee;
	protected String commissionFee;
	protected String details;
	protected String creatorPhone;
	protected String creatorName;
	protected DealState dealState;

	public DealState getDealState() {
		return dealState;
	}

	public void setDealState(DealState dealState) {
		this.dealState = dealState;
	}

	/**
	 * 3 state, need no confirm means the deal is not need to let the receiver
	 * to confirm the deal is already reached
	 */
	public enum DealState {
		Confirmed, NotConfirmed, NeedNoConfirm
	}

	public String getCreatorPhone() {
		return creatorPhone;
	}

	public void setCreatorPhone(String creator_phone) {
		this.creatorPhone = creator_phone;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getReferFee() {
		return referFee;
	}

	public void setReferFee(String referFee) {
		this.referFee = referFee;
	}

	public String getCommissionFee() {
		return commissionFee;
	}

	public void setCommissionFee(String commissionFee) {
		this.commissionFee = commissionFee;
	}
	
	/*public boolean isoverdue(){
		long day=0;
		Calendar   now   =   Calendar.getInstance();
		day = (now.getTime().getTime()- date.getTime()) / (24 * 60 * 60 * 1000);
		if(day>7)
			return true;
		else
			return false;
	} */


}
