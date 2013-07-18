package com.yjw.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.yjw.R;
import com.yjw.pojo.MsgInfo;
import com.yjw.pojo.MsgInfo.MsgType;

public class ChatListView extends ListView {

	public ChatListView(Context context) {
		super(context);
	}

	public ChatListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChatListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public static class ChatAdapter extends BaseAdapter {

		LayoutInflater inflater;
		int resourceId1;
		int resourceId2;
		List<MsgInfo> objects;

		public ChatAdapter(Context context, int textViewResourceId,
				int textViewResourceId2, List<MsgInfo> objects) {
			inflater = LayoutInflater.from(context);
			this.resourceId1 = textViewResourceId;
			this.resourceId2 = textViewResourceId2;
			this.objects = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MsgInfo chat = objects.get(position);
			convertView = inflater.inflate(
					chat.getType() == MsgType.FROM ? resourceId1 : resourceId2,
					null);
			TextView content = (TextView) convertView
					.findViewById(R.id.chatting_content_itv);
			content.setText(chat.getMsg());
			content.setEnabled(false);
			return convertView;
		}

		@Override
		public int getCount() {
			return objects.size();
		}

		@Override
		public Object getItem(int position) {
			return objects.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

	}
}
