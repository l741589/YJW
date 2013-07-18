package com.yjw.pojo;

import java.io.Serializable;
import java.util.Date;

public class MsgInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4774896756587154516L;

	public enum MsgType {
		FROM, TO
	}

	protected String msg;
	protected MsgType type;
	protected String chatId;
	protected String fromPhone;
	protected String toPhone;
	protected String dealId;
	protected Date time;

	public MsgInfo() {
		msg = null;
		type = null;
		chatId = null;
		fromPhone = null;
		toPhone = null;
		dealId = null;
		time = null;
	}

	public MsgInfo(final String msg, final MsgType type) {
		this.msg = msg;
		this.type = type;
		chatId = null;
		fromPhone = null;
		toPhone = null;
		dealId = null;
		time = null;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(final String dealId) {
		this.dealId = dealId;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(final String chatId) {
		this.chatId = chatId;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(final String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(final String toPhone) {
		this.toPhone = toPhone;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(final Date time) {
		this.time = time;
	}

	public MsgInfo(final String msg, final MsgType type, final String chatid,
			final String fromPhone, final String toPhone, final Date date) {
		this.msg = msg;
		this.type = type;
		this.chatId = chatid;
		this.fromPhone = fromPhone;
		this.toPhone = toPhone;
		this.time = date;
	}

	public final String getMsg() {
		return msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(final MsgType type) {
		this.type = type;
	}

}
