package com.yjw.pojo;

import java.util.ArrayList;
import java.util.List;

public class ExpandDealInfo {

	protected DealInfo deal;
	protected List<BaseUserInfo> replyers = new ArrayList<BaseUserInfo>();

	public ExpandDealInfo(DealInfo _deal) {
		this.deal = _deal;
	}

	public DealInfo getDeal() {
		return deal;
	}

	public void setDeal(DealInfo deal) {
		this.deal = deal;
	}

	public void addReplyer(BaseUserInfo user) {
		this.replyers.add(user);
	}

	public void addReplyer(List<BaseUserInfo> users) {
		this.replyers.addAll(users);
	}

	public List<BaseUserInfo> getReplyers() {
		return replyers;
	}
}
