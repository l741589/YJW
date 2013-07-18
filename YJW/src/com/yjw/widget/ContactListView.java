package com.yjw.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.yjw.R;
import com.yjw.pojo.ContactInfo;

public class ContactListView extends ListView {

	public ContactListView(Context context) {
		super(context);
	}

	public ContactListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ContactListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public static class ContactAdapter extends ArrayAdapter<ContactInfo> {

		LayoutInflater inflater;
		int resourceId;

		public ContactAdapter(Context context, int textViewResourceId,
				List<ContactInfo> objects) {
			super(context, textViewResourceId, objects);
			inflater = LayoutInflater.from(context);
			this.resourceId = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(resourceId, null);
			}
			ContactInfo contact = getItem(position);
			TextView name_textview = (TextView) convertView
					.findViewById(R.id.tv_name);
			TextView phone_textview = (TextView) convertView
					.findViewById(R.id.tv_phone);

			name_textview.setText(contact.getName());
			phone_textview.setText(contact.getPhone());

			return convertView;
		}

	}

}
