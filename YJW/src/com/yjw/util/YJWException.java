/*
 * Created By Yiheng Tao
 * 
 * YJWException.java
 * 
 * This is the class of customize exception for this application
 */

package com.yjw.util;

public class YJWException extends Exception {

	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -6804996939474841567L;

	@Override
	public String toString() {
		return this.getMessage();
	}

	public YJWException() {
		super("YJWException");
	}

	public YJWException(String arg) {
		super(arg);
	}

}
