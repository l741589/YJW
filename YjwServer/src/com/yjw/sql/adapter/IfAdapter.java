package com.yjw.sql.adapter;

public abstract class IfAdapter {
	abstract String condition();
	@Override
	public String toString() {
		return condition();
	}	
}
