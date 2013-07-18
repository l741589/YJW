package com.yjw.sql;

import com.yjw.bean.AccountBean;
import com.yjw.bean.UserBean;
import com.yjw.bean.ValidationBean;
import com.yjw.util.Util;

public class RegisterSQL {
	
	public String log(String s){
		System.out.println(s);
		return s;
	}
	
	/**根据UserBean里面的信息生成一条插入语句*/
	public String register(UserBean userBean) {
		if (userBean.getCellphone()==null||userBean.getPassword()==null) return null;
		return log(userBean.insert("yjw_user"));
	}
	
	/**判断手机号码是否重复
	 * @param cellphone 要判断的电话号码
	 * @return <b>int</b> 有多少个该号码存在
	 * */
	public String cellPhoneDup(String cellphone) {
		return log("select count(id) from yjw_user where cellphone='" + cellphone + "'");
	}
	
	public String validate(String sid){
		return log("select * from yjw_validation where sid='" + sid + "'");
	}
	
	public String insertValidateCode(ValidationBean bean){
		return log(bean.insert("yjw_validation"));
	}
	
	public String Login(AccountBean bean){
		return log("select id,name,cellphone,email,timestamps,balance from yjw_user where"+
				" cellphone='"+bean.getCellphone()+"'"+" and "+
				" AES_DECRYPT(password,'"+Util.KEY+"')='" +bean.getPassword() +"'");
	}

	public String cellNameDup(String name) {
		return log("select count(id) from yjw_user where name='" + name + "'");
	}

	public String del(AccountBean bean) {
		return log("DELETE FROM yjw_user WHERE cellphone='"+bean.getCellphone()+"' AND password='"+bean.getPassword()+"'");
	}
}
