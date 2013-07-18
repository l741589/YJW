package com.yjw.test;

import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yjw.bean.*;

public class Holder {
	static ExecutorService pool = Executors.newFixedThreadPool(256);
	static Vector<RegisterBean> register=new Vector<RegisterBean>();
	static Vector<UserBean> user=new Vector<UserBean>();
	static Vector<TransBean> trans=new Vector<TransBean>();
	static Vector<DealBean> deal=new Vector<DealBean>();
	static Vector<Integer> dealid=new Vector<Integer>();
	static Vector<Integer> transid=new Vector<Integer>();
	static Vector<Integer> userid=new Vector<Integer>();
	static Vector<byte[]> datas=new Vector<byte[]>();
	static Vector<AddImageBean> addimgs=new Vector<AddImageBean>();
	static Vector<ImageBean> imgs=new Vector<ImageBean>();
	static Vector<Integer> imgids=new Vector<Integer>();
}

