package com.yjw.thread.delayed;

import java.util.Vector;

import com.yjw.bean.PieceBean;
import com.yjw.ctrl.YJWControler;
import com.yjw.net.NetworkConstants;
import com.yjw.thread.YJWBaseThread;
import com.yjw.util.G;
import com.yjw.util.YJWMessage;
import com.yjw.util.shared.BigObject;

public class BufferBigObjectThread extends YJWBaseThread {
	
	private Vector<BigObject> list=new Vector<BigObject>();
	private static BufferBigObjectThread instance;
	public static BufferBigObjectThread getInstance(){
		if (instance==null) instance=YJWControler.Start(BufferBigObjectThread.class, null);
		return instance;
	}
	private BigObject obj;
	private int pid;
	private int id;
	
	synchronized public static void add(int id,int size,Object...extra){
		getInstance().list.add(new BigObject(id, size,extra));
		synchronized (getInstance()) {
			getInstance().notify();
		}
	}
	
	synchronized public static void add(int id,byte[] data,Object...extra){
		getInstance().list.add(new BigObject(id, data,extra));
		synchronized (getInstance()) {
			getInstance().notify();
		}
	}
	
	@Override
	protected void init() {
		if (list.isEmpty()&&obj==null){
			try {
				synchronized (this) {	wait();	}					
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//obj=list.firstElement();
		if (obj==null){
			obj=list.firstElement();
			list.removeElementAt(0);
		}
		initBean();
	}

	private void initBean() {	
		id=obj.getId();
		pid=obj.getPiece();
		switch(type()){
		case RECV_OBJECT:{
			bean=new PieceBean();
			((PieceBean)bean).setId(id);
			((PieceBean)bean).setPid(pid);
		}break;
		case SEND_OBJECT:{
			bean=new PieceBean();
			((PieceBean)bean).setId(id);
			((PieceBean)bean).setPid(pid);
			((PieceBean)bean).setData(obj.getPiece(pid));
		}break;			
		}
	}

	@Override
	protected String generateURL() {
		switch(obj.getMode()){
		case RECV:return NetworkConstants.URL_GETPIECE;
		case SEND:return NetworkConstants.URL_ADDPIECE;
		}
		return null;
	}
	
	@Override
	protected Type type() {
		switch(obj.getMode()){
		case RECV:return Type.RECV_OBJECT;
		case SEND:return Type.SEND_OBJECT;
		}
		return super.type();
	}
	
	@Override
	public void run() {
		while(G.getIsRun()){
			super.run();
		}
	}
	
	@Override
	protected void OnSuccess() {
		switch(obj.getMode()){
		case RECV:{
			obj.setPiece(pid,(byte[])msg.obj);
			if (obj.done()){
				msg.what=YJWMessage.GET_BIGOBJ_SUCCESS.ordinal();
				msg.arg1=obj.getId();
				msg.obj=obj;
				obj=null;
			}
		}break;
		case SEND:{
			obj.setPiece(pid);
			if (obj.done()){
				msg.what=YJWMessage.ADD_BIGOBJ_SUCCESS.ordinal();
				msg.arg1=obj.getId();
				obj=null;
			}
		}break;
		}
		super.OnSuccess();
	}	
}
