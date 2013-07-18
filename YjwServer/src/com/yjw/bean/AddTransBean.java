package com.yjw.bean;

public class AddTransBean  extends Bean{
	private int fromid;
	private UserBean[] users;
	private DealBean[] deals;
	private TransBean[] transes;
	
	public int getFromid() {
		return fromid;
	}
	public void setFromid(int fromid) {
		this.fromid = fromid;
	}
	public UserBean[] getUsers() {
		return users;
	}
	public void setUsers(UserBean[] users) {
		this.users = users;
	}
	public DealBean[] getDeals() {
		return deals;
	}
	public void setDeals(DealBean[] deals) {
		this.deals = deals;
	}
	public TransBean[] getTranses() {
		return transes;
	}
	public void setTranses(TransBean[] transes) {
		this.transes = transes;
	}
}
