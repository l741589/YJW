package com.yjw.test;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yjw.bean.AccountBean;
import com.yjw.bean.Bean;
import com.yjw.bean.DealBean;
import com.yjw.bean.GetInfoBean;
import com.yjw.bean.RegisterBean;
import com.yjw.bean.UserBean;
import com.yjw.util.Util;
import com.yjw.util.shared.ErrorCode;

public class UserTester extends TesterGroup {
	
	private static final long serialVersionUID = -1097877850168624842L;

	void addBean(String name,String password,String cellphone){
		RegisterBean bean=new RegisterBean();
		bean.setCellphone(cellphone);
		bean.setPassword(password);
		bean.setName(name);	
		Holder.register.add(bean);
	}

	public UserTester() {
		super(1);
		
	}
	
	@Override
	public void init() {
		Holder.register.clear();
		Holder.user.clear();
		addBean("Ãû×Ö","qqqqqq","11133334444");
		addBean("test2","qwerty","12345678910");
		addBean("saèóµÙ¸ÔµÄ","13233434565","10987654321");
		
		TesterGroup registerGroup=new TesterGroup(0);
		for (Bean bean:Holder.register)
			registerGroup.addTester(new YJWTester("RegisterAction",bean));
		addTester(registerGroup);
		
		TesterGroup loginGroup=new TesterGroup(0);
		for (Bean bean:Holder.register)
			loginGroup.addTester(new YJWTester("LoginAction",bean.to(AccountBean.class)){

				private static final long serialVersionUID = -47743830477758369L;

				@Override
				public boolean verify(String str) {
					boolean ret=super.verify(str);
					if (errorCode.equals(ErrorCode.E_SUCCESS)){
						UserBean b=(UserBean)beans.get(0);
						//System.out.println(b);
						Holder.user.add(b);
					}
					return ret;
				}
			});
		addTester(loginGroup);
		
		addTester(new DealTester());
		
		TesterGroup syncUserGroup=new TesterGroup(10){
			@Override
			public void init() {
				for (UserBean user:Holder.user){
					addTester(new YJWTester("SyncUserAction",Bean.Pack(user.getId().toString())));
				}
			}
		};
		
		addTester(syncUserGroup);
		
		TesterGroup getUserGroup = new TesterGroup(10){
			@Override
			public void init() {
				addTester(new YJWTester("GetUserAction"){
					@Override
					public void post(Map<String, String> map) {
						List<UserBean> beanes=Util.some(0, Holder.user);
						Integer size=beans.size();
						map.put("size", size.toString());
						for (Integer i=0;i<size;++i)
							map.put(i.toString(),((UserBean)beans.get(i)).getId().toString());
					}
				});
			}
		};
		addTester(getUserGroup);
		
		TesterGroup getUserByPhoneGroup = new TesterGroup(10){
			@Override
			public void init() {
				addTester(new YJWTester("GetUserByPhoneAction"){
					@Override
					public void post(Map<String, String> map) {
						List<UserBean> beanes=Util.some(0, Holder.user);
						Integer size=beans.size();
						map.put("size", size.toString());
						for (Integer i=0;i<size;++i)
							map.put(i.toString(),((UserBean)beans.get(i)).getCellphone().toString());
					}
				});
			}
		};
		addTester(getUserByPhoneGroup);
		
		TesterGroup delUserGroup=new TesterGroup(0);
		for (Bean bean:Holder.register)
			delUserGroup.addTester(new YJWTester("DelUserAction",bean.to(AccountBean.class)));				
		addTester(delUserGroup);
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

}
