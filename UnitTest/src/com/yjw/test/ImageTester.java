package com.yjw.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yjw.bean.AddImageBean;
import com.yjw.bean.ArrayBean;
import com.yjw.bean.Bean;
import com.yjw.bean.DataBean;
import com.yjw.bean.ImageBean;
import com.yjw.bean.NotBean;
import com.yjw.bean.PieceBean;
import com.yjw.util.Util;
import com.yjw.util.shared.BigObject;
import com.yjw.util.shared.ErrorCode;

public class ImageTester extends TesterGroup {
	
	void addData(String path){
		try {
			FileInputStream fs=new FileInputStream(path);
			ByteArrayOutputStream bs=new ByteArrayOutputStream();
			byte[] b=new byte[4096];
			int l;
			while((l=fs.read(b))>0) bs.write(b,0,l);
			fs.close();
			bs.close();
			b=bs.toByteArray();
			Holder.datas.add(b);
			AddImageBean bean=new AddImageBean();
			bean.setLength(b.length);
			bean.setMark("DEAL");
			Holder.addimgs.add(bean);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ImageTester() {
		super(1);		
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Override
	public void test() {
		super.test();
	}
	
	@Override
	public void init() {
		Holder.imgids.clear();
		Holder.datas.clear();
		Holder.imgs.clear();
		addData("assets/IMG137.png");
		addData("assets/IMG140.png");
		addData("assets/IMG141.png");
		addData("assets/IMG142.png");
		
		TesterGroup applyGroup=new TesterGroup(1);
		applyGroup.addTester(new YJWTester("ApplyImageID"){
			@Override
			public void init() {
				ArrayBean<AddImageBean> arbean=new ArrayBean<AddImageBean>();
				AddImageBean[] imgs=new AddImageBean[Holder.addimgs.size()];
				Holder.addimgs.toArray(imgs);
				arbean.setData(imgs);
				bean=arbean;
			}
			
			@Override
			public boolean verify(String str) {
				boolean ret = super.verify(str);
				if (errorCode.equals(ErrorCode.E_SUCCESS)){
					for (Bean img:beans){
						Holder.imgs.add((ImageBean)img);
						Holder.imgids.add(((ImageBean)img).getId());
					}
				}
				return ret;
			}
		});
		addTester(applyGroup);
		
		TesterGroup addpieceGroup=new TesterGroup(0){
			private BigObject obj;
			public void init() {
				for (int i=0;i<Holder.imgs.size();++i){
					obj=new BigObject(Holder.imgs.get(i).getDid(), Holder.datas.get(i));
					addTester(new YJWTester("AddPieceAction",new NotBean(obj)){
						private BigObject obj;
						int pid;
						
						@Override
						public void init() {
							PieceBean bean=new PieceBean();
							pid=obj.getPiece();
							bean.setId(obj.getId());
							bean.setPid(pid);
							bean.setData(obj.getPiece(pid));
							this.bean=bean;
						}
						
						@Override
						public Type getType() {
							return Type.SEND_OBJECT;
						}
						
						@Override
						public void test() {
							obj = (BigObject)bean.toObject();
							while(!obj.done()) super.test();
						}
						
						@Override
						public boolean verify(String str) {
							boolean ret = super.verify(str);
							if (errorCode.equals(ErrorCode.E_SUCCESS)){
								obj.setPiece(pid);
							}
							return ret;
						}
					});
				}
			};
		};
		addTester(addpieceGroup);
		
		Holder.imgs.clear();
		TesterGroup getImageGroup=new TesterGroup(0){
			public void init() {
				for (Integer x:Holder.imgids) addTester(new YJWTester("GetImageAction",Bean.Pack(x.toString())){;
					public boolean verify(String str) {
						boolean ret = super.verify(str);
						if (errorCode.equals(ErrorCode.E_SUCCESS)){
							for (Bean img:beans)
								Holder.imgs.add((ImageBean)img);
						}
						return ret;
					}
				});
			}
		};
		addTester(getImageGroup);
				
		TesterGroup getPieceGroup=new TesterGroup(0){
			private BigObject obj;
			public void init() {
				for (ImageBean img:Holder.imgs){
					obj=new BigObject(img.getDid(), img.getData().getLength());
					addTester(new YJWTester("GetPieceAction",new NotBean(obj)){
						private BigObject obj;
						int pid;
						
						@Override
						public void init() {
							PieceBean bean=new PieceBean();
							pid=obj.getPiece();
							bean.setId(obj.getId());
							bean.setPid(pid);
							this.bean=bean;
						}
						
						@Override
						public Type getType() {
							return Type.RECV_OBJECT;
						}
						
						@Override
						public void test() {
							obj = (BigObject)bean.toObject();
							while(!obj.done()) super.test();
						}
						
						@Override
						public boolean verifyData(byte[] bs) {
							boolean ret = super.verifyData(bs);
							if (ret) obj.setPiece(pid,bs);
							return ret;
						}
					});
				}
			};
		};
		addTester(getPieceGroup);
	}

}
