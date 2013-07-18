package com.yjw.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class GenerateTool {

	/**
	 * 生成唯一的sid
	 */
	public String generateSid() {

		Calendar calendar = Calendar.getInstance();
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		return Math.round(Math.random() * 100) + ""
				+ System.currentTimeMillis() + "" + minute + second
				+ Math.round(Math.random() * 1000) + "";
	}

	/* 通过sid获取user_id */
	public int getUserId(String sid) {
		return TemplateGetter.getJtl().queryForInt(
				"select id from yjw_user where sid='" + sid + "'");
	}

	/* 通过SID获取电话号码 */
	public String getPhoneNumber(String sid) {
		return TemplateGetter
				.getJtl()
				.queryForMap(
						"" + "select cellphone from yjw_user where sid='" + sid
								+ "'").get("cellphone").toString();
	}

	/* 处理电话号码的函数 */
	public ArrayList<String> getPhoneNumberList(String phoneNumber) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(phoneNumber, ",");
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list;
	}

	// 判断是否是该用户发出的消息
	public boolean isUserDeal(String dealId, String userId) {
		boolean flag = false;
		String sql = "select user_id from yjw_deal where id='" + dealId + "'";
		try {
			Map map = TemplateGetter.getJtl().queryForMap(sql);
			String queryUserId = map.get("user_id").toString();
			if (queryUserId.equals(userId)) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	/**
	 * 生成随机6位验证码
	 */
	public String genValidateString() {
		String string = "";
		Random random = new Random();
		int finalInt = 0;
		for (int i = 0; i < 6; i++) {
			int a = random.nextInt(9) + 1;
			finalInt = finalInt * 10 + a;
		}
		string = finalInt + "";
		return string;
	}
	
	/*
	 * string to date
	 * /
	 */
	
	@SuppressWarnings("deprecation")
	public Date StringToDate(String sdate){
		Date date =new Date(0, 0, 0) ;
		//Time time =new Time(0, 0, 0);
		//String dtarr[]=sdate.split(" ");
		String datearr[]=sdate.split("-");
		//String timearr[]=dtarr[1].split(":");
		date.setYear(Integer.valueOf(datearr[0]).intValue()-1900);
		date.setMonth(Integer.valueOf(datearr[1]).intValue());
		date.setDate(Integer.valueOf(datearr[2]).intValue());
	
		return date;
	}

}
