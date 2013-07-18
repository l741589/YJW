package com.yjw.util;

public class BaseBean {
	public String passwordValue(String vlaue){
		return "AES_ENCRYPT('"+vlaue+"','"+Util.KEY+"')";
	}
}
