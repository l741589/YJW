package com.yjw.test;

import java.util.List;
import java.util.Map;

import com.yjw.bean.*;
import com.yjw.util.Util;
import com.yjw.util.shared.ErrorCode;

public class TransTester extends TesterGroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7194974285410643113L;

	public TransTester() {
		super(1);
	}
	
	public void init() {
		TesterGroup addTransGroup=new TesterGroup(10);
		for (UserBean user:Holder.user){
			AddTransBean bean=new AddTransBean();
			bean.setFromid(user.getId());
			bean.setDeals(Util.list2Arr(Util.some(0, Holder.deal), DealBean.class));
			bean.setUsers(Util.list2Arr(Util.some(0, Holder.user), UserBean.class));
			addTransGroup.addTester(new YJWTester("AddTransAction",bean));
		}
		addTester(addTransGroup);
		
		TesterGroup syncTransGroup=new TesterGroup(10);
		for (UserBean user:Holder.user){
			GetInfoBean bean=user.to(GetInfoBean.class);
			bean.setEsc(false);
			bean.setPage(0);
			syncTransGroup.addTester(new YJWTester("SyncTransAction",bean){
				/**
				 * 
				 */
				private static final long serialVersionUID = -921255613245350118L;

				@Override
				public boolean verify(String str) {
					boolean ret=super.verify(str);
					for (Bean bean:beans)
						Holder.transid.add(Integer.parseInt(bean.toString()));
					return ret;
				}
			});
		}
		addTester(syncTransGroup);
		
		TesterGroup getTransGroup = new TesterGroup(10);
		getTransGroup.addTester(new YJWTester("GetTransAction"){
			/**
			 * 
			 */
			private static final long serialVersionUID = 5225669626070678974L;

			@Override
			public void post(Map<String, String> map) {
				List<Integer> is=Util.some(0, Holder.transid);
				Integer size=is.size();
				map.put("size", size.toString());
				for (Integer i=0;i<size;++i)
					map.put(i.toString(),is.get(i).toString());
			}
			
			@Override
			public boolean verify(String str) {
				boolean ret= super.verify(str);
				if (errorCode.equals(ErrorCode.E_SUCCESS)){
					Holder.trans.add((TransBean)beans.get(0));
				}
				return ret;
			}
		});
		addTester(getTransGroup);
		
		TesterGroup addTransGroup2=new TesterGroup(10){
			/**
			 * 
			 */
			private static final long serialVersionUID = 574559521661879343L;

			public void init() {
				for (UserBean user:Holder.user){
					AddTransBean bean=new AddTransBean();
					bean.setFromid(user.getId());
					bean.setTranses(Util.list2Arr(Util.some(0, Holder.trans), TransBean.class));
					bean.setUsers(Util.list2Arr(Util.some(0, Holder.user), UserBean.class));
					addTester(new YJWTester("AddTransAction",bean));
				}
			}
		};
		addTester(addTransGroup2);
		
		TesterGroup delTransGroup=new TesterGroup(10){
			/**
			 * 
			 */
			private static final long serialVersionUID = 4423439134798594168L;

			@Override
			public void init() {
				for (Integer dealid:Holder.transid){
					addTester(new YJWTester("DelTransAction",Bean.Pack(dealid)){

						/**
						 * 
						 */
						private static final long serialVersionUID = -7191642277939531714L;

						@Override
						public void post(Map<String, String> map) {
							map.put("id",bean.toString());
						}
					});
				}
			}
		};				
		addTester(delTransGroup);
	}

}
