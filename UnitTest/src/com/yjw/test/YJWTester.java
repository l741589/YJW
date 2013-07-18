package com.yjw.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yjw.bean.Bean;
import com.yjw.util.shared.ErrorCode;

public class YJWTester extends Tester {

	private static final long serialVersionUID = -2826106881431089993L;
	
	protected Bean bean = null;
	protected List<Bean> beans=new ArrayList<Bean>();
	protected ErrorCode errorCode;
	protected Map<String,Object> temp=new HashMap<String,Object>();
	
	
	public YJWTester(String action) {
		super(action);
	}
	
	public YJWTester(String action, Type type) {
		super(action,type);
	}
	
	public YJWTester(String action, Bean bean){
		super(action);
		this.bean=bean;
	}
	
	@Override
	public boolean verify(String str) {
		try{
			List<String> ss=Bean.split(str, '&');
			String strCode=ss.get(0);
			errorCode=ErrorCode.valueOf(strCode);
			if (!errorCode.equals(ErrorCode.E_SUCCESS)){
				System.err.println("!!!WARNING!!! "+ss.get(0));
			}
			for (int i=1;i<ss.size();++i){
				Bean bean=Bean.Pack(ss.get(i));
				beans.add(bean);
				if (!bean.isBean()){
					if (!(i==1&&bean.toString().equals("E_SUCCESS")))
						Long.parseLong(bean.toString());
				}				
			}
			return true;			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(getPath()+"\n"+str);
		return false;
	}
	
	@Override
	public void post(Map<String, String> map) {
		if (bean!=null){
			map.put("bean", bean.toString());
		}
		super.post(map);
	}

}
