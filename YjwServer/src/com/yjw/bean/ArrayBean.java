package com.yjw.bean;

public class ArrayBean<T>  extends Bean{
	private T[] data;

	public T[] getData() {
		return data;
	}

	public void setData(T[] data) {
		this.data = data;
	}
}
