package com.yjw.thread.async;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Handler;
import android.os.Message;

import com.yjw.activity.YJWActivity;
import com.yjw.net.NetworkConstants;
import com.yjw.net.NetworkFactory;
import com.yjw.pojo.DealInfo;
import com.yjw.pojo.MsgInfo;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.YJWMessage;

public class AddChatThread extends YJWBaseThread {

	MsgInfo chat;
	DealInfo deal;
	String to_user;

	public AddChatThread(Handler handler) {
		this.handler = handler;
	}

	public void setChat(MsgInfo chat) {
		this.chat = chat;
	}

	public void setDeal(DealInfo deal) {
		this.deal = deal;
	}

	public void setToUser(String to) {
		this.to_user = to;
	}

	@Override
	public void run() {
		String backStr = NetworkFactory.getInstance().doPost(
				generateURL(), generateParameters(),false);
		
		if(backStr!=null){
			msg = Message.obtain();
			// send message
			if (!backStr.equals("success"))
				msg.what = YJWMessage.SEND_MESSAGE_FAILURE.ordinal();
			else
				msg.what = YJWMessage.SEND_MESSAGE_SUCCESS.ordinal();
			sendMessage();
		}
	}

	@Override
	protected List<BasicNameValuePair> generateParameters() {
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("sid", YJWActivity.user.getId().toString()));
		parameters.add(new BasicNameValuePair("to_user", to_user));
		parameters.add(new BasicNameValuePair("content", chat.getMsg()));
		parameters.add(new BasicNameValuePair("deal_id", deal.getId()));
		parameters.add(new BasicNameValuePair("flag", "setMsg"));
		return parameters;
	}

	@Override
	protected String generateURL() {
		return NetworkConstants.URL_CHAT;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
}
