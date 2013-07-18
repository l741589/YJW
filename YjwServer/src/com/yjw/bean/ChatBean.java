package com.yjw.bean;

public class ChatBean  extends Bean{
	
	private Integer id;
	private Integer from_id;
	private Integer to_id;
	private Integer is_read;
	private String content;	
	private String timestamps;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(String timestamps) {
		this.timestamps = timestamps;
	}
	public Integer getIs_read() {
		return is_read;
	}
	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}
}
