package com.yjw.bean;

public class AddTransBackLogBean  extends Bean{
	private UserBean[] not_reg;
	private UserBean[] overtrans;
	private UserBean[] failed;
	
	public UserBean[] getNot_reg() {
		return not_reg;
	}
	public void setNot_reg(UserBean[] not_reg) {
		this.not_reg = not_reg;
	}
	public UserBean[] getFailed() {
		return failed;
	}
	public void setFailed(UserBean[] failed) {
		this.failed = failed;
	}
	public UserBean[] getOvertrans() {
		return overtrans;
	}
	public void setOvertrans(UserBean[] overtrans) {
		this.overtrans = overtrans;
	}

}
