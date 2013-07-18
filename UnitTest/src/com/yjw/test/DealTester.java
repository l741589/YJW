package com.yjw.test;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.yjw.bean.Bean;
import com.yjw.bean.DealBean;
import com.yjw.bean.GetInfoBean;
import com.yjw.bean.UserBean;
import com.yjw.util.Util;
import com.yjw.util.shared.ErrorCode;

public class DealTester extends TesterGroup {
	
	private static final long serialVersionUID = -311422875539435324L;

	void addDeal(String title, String content,int owner,String expiredate,float fee){
		DealBean bean=new DealBean();
		bean.setTitle(title);
		bean.setContent(content);
		bean.setOwner_id(owner);
		bean.setexpire_date(Date.valueOf(expiredate));
		bean.setFee(fee);
		Holder.deal.add(bean);
	}
	
	public DealTester() {
		super(1);		
	}
	
	@Override
	public void init() {
		
		Holder.deal.clear();
		Holder.dealid.clear();
		for (UserBean user:Holder.user){
			addDeal("业务","我是中文的内容，内容要长长长长长长长长长长长长长长长长长长长长长长长长长长长",user.getId(),"2012-12-22",1.4f);
			addDeal("deal","I am English content， the content should be longggggggggggggggggggggggg",user.getId(),"2013-08-04",5.4f);
			addDeal("test","this is test this is test this is test this is test this is test this is test",user.getId(),"2014-06-04",20.0f);
		}
		
		TesterGroup addDealGroup=new TesterGroup(10);
		for (DealBean bean:Holder.deal){
			addDealGroup.addTester(new YJWTester("AddDealAction",bean));
		}
		addTester(addDealGroup);
		
		TesterGroup syncDealGroup=new TesterGroup(10);
		for (UserBean user:Holder.user){
			GetInfoBean bean=user.to(GetInfoBean.class);
			bean.setEsc(false);
			bean.setPage(0);
			syncDealGroup.addTester(new YJWTester("SyncDealAction",bean){
				private static final long serialVersionUID = -4845804715335896468L;

				@Override
				public boolean verify(String str) {
					boolean ret=super.verify(str);
					for (Bean bean:beans)
						Holder.dealid.add(Integer.parseInt(bean.toString()));
					return ret;
				}
			});
		}
		addTester(syncDealGroup);
		
		Holder.deal.clear();
		TesterGroup getDealGroup = new TesterGroup(10);
		getDealGroup.addTester(new YJWTester("GetDealAction"){
			private static final long serialVersionUID = -1300714056679278743L;

			@Override
			public void post(Map<String, String> map) {
				List<Integer> is=Util.some(0, Holder.dealid);
				Integer size=is.size();
				map.put("size", size.toString());
				for (Integer i=0;i<size;++i)
					map.put(i.toString(),is.get(i).toString());
			}
			
			@Override
			public boolean verify(String str) {
				boolean ret=super.verify(str);
				if (errorCode.equals(ErrorCode.E_SUCCESS)){
					Holder.deal.add((DealBean)beans.get(0));
				}
				return ret;
			}
		});
		
		addTester(getDealGroup);
		
		addTester(new TransTester());
		
		TesterGroup delDealGroup=new TesterGroup(10){
			private static final long serialVersionUID = 3953364486752790238L;

			@Override
			public void init() {
				for (Integer dealid:Holder.dealid){
					addTester(new YJWTester("DelDealAction",Bean.Pack(dealid)){
						private static final long serialVersionUID = -8150499896644464083L;

						@Override
						public void post(Map<String, String> map) {
							map.put("id",bean.toString());
						}
					});
				}
			}
		};				
		addTester(delDealGroup);
		
		
	}


}
