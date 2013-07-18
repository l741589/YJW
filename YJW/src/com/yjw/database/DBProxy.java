/*
 *     Created by “‡∫„ Ã’ 
 *  Copyright (c) 2012ƒÍ . All rights reserved.
 *  
 *  This class is used to combine all the database manipulations.
 */

package com.yjw.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.yjw.activity.YJWActivity;
import com.yjw.bean.AccountBean;
import com.yjw.bean.Bean;
import com.yjw.bean.UserBean;
import com.yjw.pojo.MsgInfo;
import com.yjw.pojo.MsgInfo.MsgType;

public class DBProxy {
	
	public static Map<String,String> curToMap(Cursor cur){
		HashMap<String,String> map=new HashMap<String, String>();
		String[] keys=cur.getColumnNames();
		cur.moveToFirst();
		for (String key:keys){
			int col=cur.getColumnIndex(key);
			String value=cur.getString(col);
			map.put(key, value);
		}
		return map;
	}
	
	

	/**
	 * @description given the dealId and chatWith(in phone number format),
	 *              return the list of the messages in the database
	 * @name getHistoryMessage
	 * @param
	 * @category database select
	 * @author Yiheng Tao
	 */
	
	public static List<MsgInfo> getHistoryMessage(String dealId, String chatWith) {
		List<MsgInfo> msgList = new ArrayList<MsgInfo>();
		Cursor cur = YJWActivity.database.query(DBStatic.MessageTableName,
				DBStatic.MessageTableColumns, "dealid = " + dealId
						+ " and chat_user = " + chatWith, null, null, null,
				null);
		while (cur.moveToNext()) {
			MsgInfo chat = new MsgInfo(cur.getString(3), cur.getString(4)
					.equals("from") ? MsgType.FROM : MsgType.TO);
			Log.d("char", chat.getMsg());
			msgList.add(chat);
		}
		return msgList;
	}

	/**
	 * @description insert into database with the given dealid and phone number
	 *              to chat and the message
	 * @name insertChatMessage
	 * @param
	 * @category database insertion
	 * @author Yiheng Tao
	 */
	public static void insertChatMessage(String dealId, String chatWith,MsgInfo msg) {
		ContentValues cv = new ContentValues();
		cv.put("dealid", dealId);
		cv.put("chat_user", chatWith);
		cv.put("type", msg.getType().equals(MsgType.FROM) ? "from" : "to");
		cv.put("msg", msg.getMsg());
		YJWActivity.database.insert(DBStatic.MessageTableName, null, cv);
	}
	
	/**
	 * insert the account into the database
	 * @param id
	 * @param phone
	 * @param sid
	 */
	public static void insertNewAccount(AccountBean bean)
	{
		YJWActivity.database.execSQL(bean.insert(DBStatic.AccountTableName));
	}
	
/*	public static void insertDeals(List<DealInfo> list, String type){
		for(DealInfo deal:list){
			Cursor cur = YJWActivity.database.query(DBStatic.DealTableName,
					DBStatic.DealTableColumns, "deal_id = "+deal.getId()+" and type = '"+type+"'",null,null,null,null);
			if (cur != null && 0 != cur.getCount()) {
				//Log.d("dealInfo","already exists");
			} else {
				ContentValues cv = new ContentValues();
				cv.put("type", type);
				cv.put("deal_id", deal.getId());
				cv.put("title", deal.getTitle());
				cv.put("content", deal.getDetails());
				cv.put("expeir_date", deal.getDate().toString());
				cv.put("creator", deal.getCreator());
				cv.put("referfee", deal.getReferFee());
				cv.put("commissionfee", deal.getCommissionFee());
				cv.put("creatorphone", deal.getCreatorPhone());
				cv.put("creatorname", deal.getCreatorName());
				switch(deal.getDealState()){
				case Confirmed:
					cv.put("dealstate", "Confirmed");
					break;
				case NotConfirmed:
					cv.put("dealstate", "NotConfirmed");
					break;
				case NeedNoConfirm:
					cv.put("dealstate", "NeedNoConfirm");
					break;
				}

				YJWActivity.database.insert(DBStatic.DealTableName, null, cv);
			}		
		}
	}
	
	public static void insertDeals(List<ExpandDealInfo>list){
		for(ExpandDealInfo expdeal:list){
			DealInfo deal = expdeal.getDeal();
			List<BaseUserInfo> user_list= expdeal.getReplyers();
			Cursor cur = YJWActivity.database.query(DBStatic.DealTableName,
					DBStatic.DealTableColumns, "deal_id = "+deal.getId()+" and type = 'PUB'",null,null,null,null);
			if (cur != null && 0 != cur.getCount()) {
				//Log.d("dealInfo","already exists");
			} else {
				ContentValues cv = new ContentValues();
				cv.put("type", "PUB");
				cv.put("deal_id", deal.getId());
				cv.put("title", deal.getTitle());
				cv.put("content", deal.getDetails());
				cv.put("expeir_date", deal.getDate().toString());
				cv.put("creator", deal.getCreator());
				cv.put("referfee", deal.getReferFee());
				cv.put("commissionfee", deal.getCommissionFee());
				switch(deal.getDealState()){
				case Confirmed:
					cv.put("dealstate", "Confirmed");
					break;
				case NotConfirmed:
					cv.put("dealstate", "NotConfirmed");
					break;
				case NeedNoConfirm:
					cv.put("dealstate", "NeedNoConfirm");
					break;
				}
				for(BaseUserInfo user: user_list){
					cv.put("creatorphone", deal.getCreatorPhone());
					cv.put("creatorname", deal.getCreatorName());
					YJWActivity.database.insert(DBStatic.DealTableName, null, cv);
				}
			}		
		}
	}*/
	
	public static void clearAccountTable(){
		YJWActivity.database.execSQL(DBStatic.clearAccountTable);
	}
	
	public static void insertBean(Bean bean,String tableName){
		YJWActivity.database.execSQL(bean.insert(tableName));
	}
	public static void insert(String table,ContentValues cv){
		YJWActivity.database.insert(table, null, cv);
	}
	
	
	public static<T extends Bean> T selectBean(String tableName, String selection,Class<T> type){
		Cursor cur=YJWActivity.database.query(tableName, null, selection, null , null, null, null);
		T ret=null;
		if (cur!=null&&cur.getCount()!=0) {
			ret = Bean.Pack(curToMap(cur),type);			
		}		
		cur.close();
		return ret;
	}
	
	public static void execSQL(String sql){
		YJWActivity.database.execSQL(sql);
	}
	
	public static void inserUserToContactsBook(Context context,UserBean bean){
		ContentResolver cr=context.getContentResolver();
	    Cursor num=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.NUMBER+" = '"+bean.getCellphone()+"'", null, null);
	    long rawContactsId = 0;
	    if (num.getCount()<0) {
	    	num.close();
	    	return;
	    }
	    if (num.getCount()==0){
	    
	    	ContentValues values = new ContentValues ();  
	    	Uri rawContactUri = cr.insert(ContactsContract.RawContacts.CONTENT_URI,values);   
	    	long id = ContentUris.parseId(rawContactUri);   
	    
	    	values.clear();     
	    	values.put(ContactsContract.CommonDataKinds.StructuredName.RAW_CONTACT_ID,id);     
	    	values.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);   
	    	values.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,bean.getName());     
	    	cr.insert(ContactsContract.Data.CONTENT_URI,values);   
	    	values.clear();   
	    	values.put(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID,id);     
	    	values.put(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);   
	    	values.put(ContactsContract.CommonDataKinds.Phone.NUMBER,bean.getCellphone());   
	    	cr.insert(ContactsContract.Data.CONTENT_URI,values);
	    	//Log.d("DBProxy","contact not exists");
	    	rawContactsId=id;
	    }else {
	    	//Log.d("DBProxy","contact exists");
	    	num.moveToFirst();
		    rawContactsId=num.getLong(num.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
	    }
	    	    
	    num.close();
	    addContactToGroup(cr, rawContactsId, getGroupId(cr,null));
	}
	
	
	private static void addContactToGroup(ContentResolver rs,long contactId,long groupId) {
        boolean b1 = ifExistContactInGroup(rs,contactId, groupId);
        if (b1) {
        		//Log.d("DBProxy","relation exists");
                return;
        } else {
        	//Log.d("DBProxy","cid:"+contactId+", gid:"+groupId);
        	ContentValues values = new ContentValues();
        	values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID,contactId); 
        	values.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,groupId);
        	values.put(ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
        	Uri uri=rs.insert(ContactsContract.Data.CONTENT_URI, values);
        	
            //if (uri!=null) Log.d("DBProxy",uri.toString());
            //else Log.d("DBProxy","URI NULL");
        }
	}

	private static boolean ifExistContactInGroup(ContentResolver rs,long contactId, long groupId) {
        String where = ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE
                        + "' AND " + ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID + " = '" + groupId
                        + "' AND " + ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID + " = '" + contactId + "'";
        Cursor markCursor = rs.query(ContactsContract.Data.CONTENT_URI, null, where, null, null);
        //new String[]{ContactsContract.Data.DISPLAY_NAME}
        if (markCursor.moveToFirst()) {
        		markCursor.close();
                return true;
        }else {
        		markCursor.close();
                return false;
        }
	}
	
	private static long getGroupId(ContentResolver cr,String groupname){
		String name="YJW";
		if (groupname!=null) name+=":"+groupname;
		Cursor cur=cr.query(ContactsContract.Groups.CONTENT_URI, null, 
				ContactsContract.Groups.TITLE + " = '" + name + "'", null, null);
		if (cur.getCount()>0) {
			cur.moveToFirst();
			long id=cur.getLong(cur.getColumnIndex(ContactsContract.Groups._ID));
			int deleted;
			while((deleted=cur.getInt(cur.getColumnIndex(ContactsContract.Groups.DELETED)))==1){
				//Log.d("DBProxy","group deleted: "+id);
				if (!cur.moveToNext()) break;
			}
			if (deleted==0){				
				//Log.d("DBProxy","group exists: "+id+" deleted:"+deleted);
				cur.close();
				return id;
			}
		}
		ContentValues values = new ContentValues();		
		//values.put(ContactsContract.Data.MIMETYPE,ContactsContract.Groups.CONTENT_ITEM_TYPE);
		values.put(ContactsContract.Groups.TITLE, name);
		Uri uri=cr.insert(ContactsContract.Groups.CONTENT_URI,values);
		long id=ContentUris.parseId(uri);
		//Log.d("DBProxy","group not exists: "+id);
		cur.close();
		return id;
	}
	
	static public byte[] selectBlob(String table,int id){
		Cursor cur=YJWActivity.database.query(table,new String[]{"data"}, "id='"+id+"'",null, null, null, null);
		if (cur.moveToFirst())
			return cur.getBlob(cur.getColumnIndex("data"));
		else return null;
	}
}
