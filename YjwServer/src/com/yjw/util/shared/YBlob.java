package com.yjw.util.shared;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

public class YBlob {
	
	private byte[] bytes;
	
	public YBlob(byte[] ar){
		super();
		bytes=ar;
	}
	public YBlob(Blob o) {
		try {
			InputStream is=o.getBinaryStream();
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			byte[] bs=new byte[1024];
			int len;
			while ((len=is.read(bs))>0){
				os.write(bs, 0, len);	
			}					
			bytes=os.toByteArray();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public byte[] getBytes(){
		return bytes;
	}
}
