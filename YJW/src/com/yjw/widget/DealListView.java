/*
 * 
 *     Created by 亦恒 陶 
 *  Copyright (c) 2012年 . All rights reserved.
 *  
 */
package com.yjw.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yjw.R;
import com.yjw.pojo.BaseDealInfo;
import com.yjw.pojo.BaseDealInfo.DealState;
import com.yjw.pojo.DealInfo;
import com.yjw.thread.async.ConfirmDealThread;
import com.yjw.util.YJWMessage;

public class DealListView extends ListView {

	public DealListView(Context context) {
		super(context);
	}

	public DealListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DealListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public static class DealListAdapter extends ArrayAdapter<DealInfo> {
		LayoutInflater inflater;
		int resourceId;
		List<DealInfo> objects;

		public DealListAdapter(Context context, int textViewResourceId,
				List<DealInfo> objects) {
			super(context, textViewResourceId, objects);
			inflater = LayoutInflater.from(context);
			this.objects = objects;
			this.resourceId = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// if convert view is null, then create a new one
			if (convertView == null) {
				convertView = inflater.inflate(resourceId, null);
			}
			final DealInfo deal = getItem(position);
			convertView = fillDealWithConvertView(convertView, deal);
			if(deal.canForward == false)
				if(deal.getDealState()==BaseDealInfo.DealState.Confirmed)
					if(deal.isCreator)
						convertView.setBackgroundColor(Color.GREEN);
			// TODO these codes are only intend for testing the feature, the
			// design should be changed
			if(!deal.isCreator )
			if (deal.getDealState() == DealState.NotConfirmed) {
				final LinearLayout confirmLayout = (LinearLayout) convertView
						.findViewById(R.id.confirm_layout);
				confirmLayout.setVisibility(View.VISIBLE);
				Button acceptButton = (Button) convertView
						.findViewById(R.id.bt_accept);
				Button rejectButton = (Button) convertView
						.findViewById(R.id.bt_reject);
				confirmLayout.setTag("ConfirmLayout");

				final Handler handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						switch (YJWMessage.values()[msg.what]) {
						case CONFIRM_ACCEPT_SUCCESS:
							Toast.makeText(getContext(), "确认成功",
									Toast.LENGTH_SHORT).show();
							confirmLayout.setVisibility(View.GONE);
							break;
						case CONFIRM_DECLINE_SUCCESS:
							Toast.makeText(getContext(), "拒绝成功",
									Toast.LENGTH_SHORT).show();
							objects.remove(deal);
							break;
						case CONFIRM_DECLINE_FAILURE:
						case CONFIRM_ACCEPT_FAILURE:
							Toast.makeText(getContext(), "操作失败",
									Toast.LENGTH_SHORT).show();
							break;
						}
					}
				};
				acceptButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ConfirmDealThread gdt = new ConfirmDealThread(deal
								.getId(), handler, true);
						new Thread(gdt).start();
					}
				});

				rejectButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ConfirmDealThread gdt = new ConfirmDealThread(deal
								.getId(), handler, false);
						new Thread(gdt).start();
					}
				});
			}
			return convertView;
		}

		public static View fillDealWithConvertView(View convertView,
				DealInfo deal) {

			TextView title_textview = (TextView) convertView
					.findViewById(R.id.deal_item_title);
			TextView date_textview = (TextView) convertView
					.findViewById(R.id.deal_item_date);
			TextView from_textview = (TextView) convertView
					.findViewById(R.id.deal_item_from);
			TextView money_textview = (TextView) convertView
					.findViewById(R.id.deal_item_money);

			title_textview.setText(deal.getTitle() == null ? "" : deal
					.getTitle());
			date_textview.setText(deal.getDate() == null ? "" : deal.getDate()
					.toGMTString());
			from_textview.setText(deal.getCreatorName() == null ? "" : deal
					.getCreatorName());
			money_textview.setText("介绍新客户" + deal.getReferFee() + "元，成功"
					+ deal.getCommissionFee() + "元");

			return convertView;
		}
	}

}
