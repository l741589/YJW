package com.yjw.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.util.Assert;

public class Tester extends Thread implements Serializable,Cloneable,Callable<Object> {
	
	private static final long serialVersionUID = -918328987636005695L;
	private String action = null;
	private Type type = null;
	protected String path ="";
	private String way="";
	
	public Type getType() { return type;}
	public String getAction(){ return action; }
	
	public void post(Map<String,String> map){return;}	
	public void postData(ByteArrayOutputStream stream){return;}
	public boolean verify(String str){System.out.println(getPath()+"\n"+str);return false;}
	public boolean verifyData(byte[] data){return false;}
	
	public String getPath(){
		if (path.equals("")) path+=getAction(); 
		return path+"."+way;
	};
	
	public void setPath(String path){
		this.path=path;
	}
	
	public Tester(String action){
		this.action = action;
		this.type = Type.STRING;
	}
	
	public Tester(String action, Type type){
		this.action = action;
		this.type = type;
	}

	public static Tester clone(Tester src){
		
		try {
			ByteArrayOutputStream ob=new ByteArrayOutputStream();
			ObjectOutputStream output=new ObjectOutputStream(ob);
			output.writeObject(src);
			byte[] bs=ob.toByteArray();
			ByteArrayInputStream ib=new ByteArrayInputStream(bs);
			ObjectInputStream input=new ObjectInputStream(ib);
			return (Tester)input.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(ClassCastException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void init(){};
	
	public void test(){
		init();
		switch(getType()){
		case STRING:
			//way="GET";
			//Assert.isTrue(verify(NetworkFactory.doGet(this)));
			way="POST";
			Assert.isTrue(verify(NetworkFactory.doPost(this)));
			break;
		case SEND_OBJECT:
			way="SENDDATA";
			Assert.isTrue(verify(NetworkFactory.doPostDataSend(this)));
			break;
		case RECV_OBJECT:
			way="RECVDATA";
			Assert.isTrue(verifyData(NetworkFactory.doPostDataRecv(this)));
			break;
		}
		System.out.println(getPath()+" is Done");
	}
	
	@Override
	public void run() {
		test();
		super.run();
	}
	@Override
	public Object call() throws Exception {
		test();
		return null;
	}
	
}
