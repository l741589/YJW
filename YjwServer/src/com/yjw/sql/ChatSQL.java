package com.yjw.sql;

import com.yjw.bean.GetInfoBean;
import com.yjw.sql.adapter.AndAdapter;
import com.yjw.sql.adapter.IfChatFrom;
import com.yjw.sql.adapter.IfChatIsRead;
import com.yjw.sql.adapter.IfChatTo;

public class ChatSQL extends BaseSQL{
	
	public String sync(GetInfoBean bean) {
		return sync(bean,new AndAdapter(
				new IfChatFrom(bean.getId()),
				new IfChatTo(bean.getId()),
				new IfChatIsRead(bean.getArg1())
				));
				
	}
	
	public String Table() {
		return "yjw_chat";
	}
}
