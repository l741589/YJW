/*
 * Created By Yiheng Tao
 * ChatUserInfoAdapter.java
 * 
 * This adapter is used in DealDetailPageActivity and for displaying the user who has received the deal.
 * It is only needed when the user is the deal creator
 * The function getView is customized for R.layout.chat_user_item in which the name and phone should be filled with
 * the information given by the BaseUserInfo
 */

package com.yjw.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.yjw.R;
import com.yjw.pojo.BaseUserInfo;

public class ChatUserInfoAdapter extends ArrayAdapter<BaseUserInfo> {

	LayoutInflater inflater;
	int resourceId;

	public ChatUserInfoAdapter(Context context, int textViewResourceId,
			List<BaseUserInfo> objects) {
		super(context, textViewResourceId, objects);
		inflater = LayoutInflater.from(context);
		this.resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(resourceId, null);
		}
		BaseUserInfo user = getItem(position);
		TextView name_textview = (TextView) convertView
				.findViewById(R.id.chat_user_name);
		TextView phone_textview = (TextView) convertView
				.findViewById(R.id.chat_user_phone);

		name_textview.setText(user.getRealName());
		phone_textview.setText(user.getPhoneNumber());

		return convertView;
	}
}
