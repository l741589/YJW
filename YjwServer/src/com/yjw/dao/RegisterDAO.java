package com.yjw.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.yjw.bean.AccountBean;
import com.yjw.bean.Bean;
import com.yjw.bean.UserBean;
import com.yjw.bean.ValidationBean;
import com.yjw.sql.RegisterSQL;
import com.yjw.util.TemplateGetter;
import com.yjw.util.shared.ErrorCode;

/**
 * 注册的封装代码
 * 
 * @author Glorabit
 * 
 */
public class RegisterDAO {

	private JdbcTemplate jdbcTemplate;
	private RegisterSQL sql;

	public RegisterDAO() {
		this.jdbcTemplate = TemplateGetter.getJtl();
		this.sql = new RegisterSQL();
	}

	// 注册信息插入数据库
	public boolean register(UserBean userBean) {
		boolean flag = false;
		// 插入数据库
		try{
			int i = jdbcTemplate.update(sql.register(userBean));
			if (i != 0) {
				flag = true;
			}
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	// 判断该手机号是否已经存在
	public boolean isCellphoneDuplicate(String cellphone) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(sql.cellPhoneDup(cellphone));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}
	
	public boolean isNameDuplicate(String name) {
		boolean flag = false;
		int i = jdbcTemplate.queryForInt(sql.cellNameDup(name));
		if (i != 0) {
			flag = true;
		}
		return flag;
	}	

	// 登录
	@SuppressWarnings("unchecked")
	public String login(AccountBean bean) {
		String msg = "";
		if (isCellphoneDuplicate(bean.getCellphone())) {
			String sqlstr=sql.Login(bean);
			try{
				Map<String,?> map= this.jdbcTemplate.queryForMap(sqlstr);
				msg = ErrorCode.E_SUCCESS+"&"+Bean.Pack(map,UserBean.class);
			}catch(IncorrectResultSizeDataAccessException e){		
				msg = ErrorCode.E_LOGIN_FAILED.toString();
			}catch(DataAccessException e){
				msg = ErrorCode.E_LOGIN_FAILED.toString();
				System.out.println("please check table: yjw_user");
				e.printStackTrace();
			}
		}else{
			msg = ErrorCode.E_LOGIN_FAILED.toString();
		}
		return msg;
	}

	public String delUser(AccountBean bean){
		String msg = "";
		if (isCellphoneDuplicate(bean.getCellphone())) {
			String sqlstr=sql.Login(bean);
			try{
				Map<String,?> map= this.jdbcTemplate.queryForMap(sqlstr);
				UserBean ub=Bean.Pack(map,UserBean.class);
				if (new UserDAO().del(ub.getId()))
					msg = ErrorCode.E_SUCCESS.toString();
				else 
					msg = ErrorCode.E_DEL_USER_FAILED.toString();
			}catch(IncorrectResultSizeDataAccessException e){		
				msg = ErrorCode.E_DEL_USER_FAILED.toString();
			}catch(DataAccessException e){
				msg = ErrorCode.E_DEL_USER_FAILED.toString();
				System.out.println("please check table: yjw_user");
				e.printStackTrace();
			}
		}else{
			msg = ErrorCode.E_DEL_USER_FAILED.toString();
		}
		return msg;
	}
	// 插入验证信息
	public boolean insertValidateCode(ValidationBean bean) {
		String insertSQL = sql.insertValidateCode(bean);
		int insertReturn = this.jdbcTemplate.update(insertSQL);
		return insertReturn > 0;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validate(String sid, String code) {
		boolean flag = false;
		String getValidateRecordSQL = sql.validate(sid);
		List<Map<String,Object>> list = this.jdbcTemplate.queryForList(getValidateRecordSQL);
		if (list.size() == 1) {
			Map<String,Object> map = list.get(0);
			if (code.equals(map.get("validation_code"))) {
					flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
}
