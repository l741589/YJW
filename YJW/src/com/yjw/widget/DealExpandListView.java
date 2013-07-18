package com.yjw.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.yjw.R;
import com.yjw.pojo.DealInfo;
import com.yjw.pojo.ExpandDealInfo;

public class DealExpandListView extends ExpandableListView {

	public DealExpandListView(Context context) {
		super(context);
	}

	public DealExpandListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DealExpandListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public static class DealExpandListAdapter extends BaseExpandableListAdapter {

		LayoutInflater inflater;
		int groupResourceId;
		int childResourceId;
		List<ExpandDealInfo> objects;

		public DealExpandListAdapter(Context context, int _groupResourceId,
				int _childResourceId, List<ExpandDealInfo> objects) {
			inflater = LayoutInflater.from(context);
			this.groupResourceId = _groupResourceId;
			this.childResourceId = _childResourceId;
			this.objects = objects;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return objects.get(groupPosition).getReplyers().get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(childResourceId, null);
			}
			TextView tv_name = (TextView) convertView
					.findViewById(R.id.chat_user_name);
			TextView tv_phone = (TextView) convertView
					.findViewById(R.id.chat_user_phone);

			String name = objects.get(groupPosition).getReplyers()
					.get(childPosition).getRealName();
			String phone = objects.get(groupPosition).getReplyers()
					.get(childPosition).getPhoneNumber();
			tv_name.setText(name);
			tv_phone.setText(phone);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return objects.get(groupPosition).getReplyers().size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return objects.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return objects.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = inflater.inflate(groupResourceId, null);
			DealInfo deal = objects.get(groupPosition).getDeal();
			convertView = DealListView.DealListAdapter.fillDealWithConvertView(
					convertView, deal);
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

}
