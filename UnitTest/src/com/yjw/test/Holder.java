package com.yjw.test;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yjw.bean.DealBean;
import com.yjw.bean.RegisterBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;

public class Holder {
	static ExecutorService pool = Executors.newFixedThreadPool(256);
	static Vector<RegisterBean> register=new Vector<RegisterBean>();
	static Vector<UserBean> user=new Vector<UserBean>();
	static Vector<TransBean> trans=new Vector<TransBean>();
	static Vector<DealBean> deal=new Vector<DealBean>();
	static Vector<Integer> dealid=new Vector<Integer>();
	static Vector<Integer> transid=new Vector<Integer>();
	static Vector<Integer> userid=new Vector<Integer>();
}
