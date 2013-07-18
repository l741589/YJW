package com.yjw.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.util.Log;

public class BigObject{
	static public final int PIECE_SIZE = 4096;
	private int id;
	private int size;
	private int count;
	private int rate;
	private byte[][] pieces;
	public static enum Mode{SEND,RECV};
	private Mode mode;
	private Object[] extra;
	
	/**判断是否完全完成*/
	public boolean done(){
		return count==rate;
	}
	
	/**接收模式构造函数*/
	public BigObject(int id,int size,Object... extra){
		this.size=size;
		this.id=id;
		this.count=((size-1)/4096)+1;
		this.pieces=new byte[this.count][];
		this.extra=extra;
		this.rate=0;
		this.mode=Mode.RECV;
	}
	
	/**发送模式构造函数*/
	public BigObject(int id,byte[] data,Object... extra) {
		this.size=data.length;
		this.id=id;
		this.count=((size-1)/4096)+1;
		this.pieces=new byte[this.count][];
		this.extra=extra;
		this.rate=0;		
		this.mode=Mode.SEND;
		try {
			int c=this.size;
			ByteArrayInputStream is=new ByteArrayInputStream(data);
			for (int i=0;i<this.count;++i){
				int l=Math.min(c, PIECE_SIZE);
				c-=PIECE_SIZE;
				this.pieces[i]=new byte[l];
					is.read(pieces[i]);				
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	/**RECV模式获取需接收分片*/
	public int getPieceToReceive(){
		if (done()){
			Log.e("BigObject","Not Done");
			return -1;
		}
		int i=rate;
		while (pieces[i]!=null) {
			++i;
			if (i>=count) i-=count;
		}
		return i;
	}
	
	/**SEND模式获取需发送分片*/
	public int getPieceToSend(){
		if (done()) {
			Log.e("BigObject","Not Done");
			return -1;
		}
		int i=rate;
		while (pieces[i]==null) {
			++i;
			if (i>=count) i-=count;
		}
		return i;
	}
	/**根据模式获取需处理的分片编号*/
	public int getPiece(){
		switch(mode){
		case RECV:return getPieceToReceive();
		case SEND:return getPieceToSend();
		}
		Log.e("BigObject","Unexperct Mode");
		return -1;
	}
	
	/**获取分片*/
	public byte[] getPiece(int i){
		return pieces[i];
	}
	
	/**RECV模式添加已接收分片*/
	public void setPiece(int i,byte[] data){
		if (pieces[i]==null&&data!=null) ++rate; 
		pieces[i]=data;
	}
	
	/**SEND模式反馈已发送分片*/
	public void setPiece(int i){
		++rate;
		pieces[i]=null;
	}		
	
	public Mode getMode(){
		return mode;
	}
	
	public int getId(){
		return id;
	}
	
	public byte[] getData(){
		if (mode==Mode.SEND) return null;
		if (!done()) return null;		
		try{
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			for (byte[] p:pieces){
				os.write(p);
			}
			os.close();
			return os.toByteArray();
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Object[] getExtra(){
		return extra;
	}
	
	public Object getExtra(int i){
		return extra[i];
	}
}