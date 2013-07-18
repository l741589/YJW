package com.yjw.sql.adapter;

public class IfChatIsRead extends IfAdapter {
	
	Integer isRead;
	
	public IfChatIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	@Override
	String condition() {
		if (isRead==null)return null;
		else return "is_read='"+isRead+"'";
	}

}
