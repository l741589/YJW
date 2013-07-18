/*
 * 
 *     Created by 亦恒 陶 
 *  Copyright (c) 2012年 . All rights reserved.
 *  
 */
package com.yjw.database;

import java.lang.reflect.Method;

import android.util.Log;

public class DBStatic {

	
	// 数据库表
	public static String AccountTableName = "account";
	public static String MessageTableName = "message";
	public static String DealTableName = "deal";
	public static String UserTableName = "user";
	public static String TransTableName = "trans";
	public static String ImageTableName = "image";

	// 数据库表的列名
	public static final String[] MessageTableColumns = new String[] { "id", "deal", "from_id", "to_id", "content", "type" };

	public static final String CreateMessageTable = "CREATE TABLE IF NOT EXISTS "
			+ MessageTableName
			+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, deal INTEGER, from_id INTEGER, to_id INTEGER," 
			+ " content TEXT, type TEXT)";
	public static final String CreateImageTable = "CREATE TABLE IF NOT EXISTS "
			+ ImageTableName + " (id INTEGER, data MEDIUMBLOB)";
	
	public static String clearAccountTable = "delete from " + AccountTableName;
	
	public static String deleteUserByPhone(String phone){
		return "delete from " + UserTableName + " where cellphone='"+phone+"'";
	}

	public static String CreateTableByBean(Class<?> beanclass,String tableName){
		String s="CREATE TABLE IF NOT EXISTS "+tableName+" ";
		Method[] methods=beanclass.getMethods();
		boolean first=true;
		for (Method method:methods){
			String name=method.getName();
			if (name.length()>3&&name.substring(0,3).equals("get")&&!name.equals("getClass")){
				if (first) {
					s+="(";
					first=false;
				}else
					s+=",";
				s+=method.getName().substring(3).toLowerCase()+" TEXT";
			}
		}
		s+=")";
		Log.i("Database",s);
		return s;
	}
}
