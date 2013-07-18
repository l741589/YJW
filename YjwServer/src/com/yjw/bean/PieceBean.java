package com.yjw.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.yjw.util.Util;

public class PieceBean extends Bean {
	private int id;
	private int pid;
	private byte[] data;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public byte[] toBytes(){
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		try {
			os.write(Util.int2bytes(id));
			os.write(Util.int2bytes(pid));
			os.write(getData());
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return os.toByteArray();
	}
}
