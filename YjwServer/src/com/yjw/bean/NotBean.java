package com.yjw.bean;

/**用于容错*/
public final class NotBean extends Bean{
	
	private Object o;
	
	public NotBean(Object o) {
		this.o=o;
		//System.out.println(o.toString()+" is not a Bean");
	}
	
	/**当不能确定该对象是否为bean时必须用此函数获取真正对象*/
	@Override
	public Object toObject() {
		return o;
	}
	
	@Override
	public String toString() {
		return o.toString();
	}

	@Override
	public boolean isBean() {
		return false;
	}
	
	@Override
	public <T extends Bean> T to(Class<T> cls) {
		return cast(this,cls);
	}
	
	@Override
	public String insert(String table) {
		System.err.println("Only Bean can be inserted!");
		return null;
	}
	
	@Override
	public String update(String table) {
		System.err.println("Only Bean can be updated!");
		return null;
	}
}
