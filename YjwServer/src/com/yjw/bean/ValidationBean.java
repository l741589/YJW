package com.yjw.bean;

public class ValidationBean extends Bean {
	private String sid;
	private String validation_code;
	private String timestamp;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getValidation_code() {
		return validation_code;
	}

	public void setValidation_code(String validation_code) {
		this.validation_code = validation_code;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
