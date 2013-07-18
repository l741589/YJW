/*
 * Created By Yiheng Tao
 * JSONParser.java
 * 
 * This class is used for parse JSONObject to other object which will be used
 * All methods should be STATIC and should throws some exceptions
 * 
 */

package com.yjw.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.yjw.pojo.BaseDealInfo.DealState;
import com.yjw.pojo.BaseUserInfo;
import com.yjw.pojo.DealInfo;
import com.yjw.pojo.ExpandDealInfo;
import com.yjw.pojo.MsgInfo;

public class JSONParser {

	public static ExpandDealInfo parseJsonToExpandDealInfo(JSONObject obj)
			throws JSONException {
		DealInfo deal = parseJsonToDeal(obj);
		ExpandDealInfo expandDeal = new ExpandDealInfo(deal);
		if (obj.has("chatUsers")) {
			JSONObject users = obj.getJSONObject("chatUsers");
			for (int i = 1;; i++) {
				if (users.has(new Integer(i).toString())) {
					JSONObject obj2 = users.getJSONObject(new Integer(i)
							.toString());
					expandDeal.addReplyer(new BaseUserInfo("0", obj2
							.getString("cellphone"), obj2.getString("name")));
				} else
					break;
			}
		}
		return expandDeal;
	}

	/**
	 * function parse the deal Json object to deal object
	 * 
	 * @name parseJsonToDeal
	 * @param JSONObject
	 * @return DealInfo
	 * @throws JSONException
	 * @author Yiheng Tao
	 */
	public static DealInfo parseJsonToDeal(JSONObject obj) throws JSONException {
		DealInfo deal = new DealInfo();
		if (obj.has("id"))
			deal.setId(obj.getString("id"));
		if (obj.has("user_id"))
			deal.setCreator(obj.getString("user_id"));
		if (obj.has("title"))
			deal.setTitle(obj.getString("title"));
		if (obj.has("fee"))
			deal.setReferFee(obj.getString("fee"));
		if (obj.has("commission"))
			deal.setCommissionFee(obj.getString("commission"));
		if (obj.has("timestamp")) {
			// 2012-04-04 18:44:51
			String time = obj.getString("timestamp");
			try {
				deal.setDate(JSONParser.parseMySQLDateFormat(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (obj.has("type")) {
			deal.canForward = obj.getString("type").equals("NEW") ? true
					: false;
		}
		if (obj.has("cellphone")) {
			deal.setCreatorPhone(obj.getString("cellphone"));
		}
		if (obj.has("name")) {
			deal.setCreatorName(obj.getString("name"));
		}
		if (obj.has("req_confirm")) {
			String str = obj.getString("req_confirm");
			if (str.equalsIgnoreCase("no")) {
				deal.setDealState(DealState.NeedNoConfirm);
			} else if (str.equalsIgnoreCase("yes")
					&& obj.getString("rec_status").equalsIgnoreCase("accept")) {
				deal.setDealState(DealState.Confirmed);
			} else {
				deal.setDealState(DealState.NotConfirmed);
			}
		}
		return deal;
	}

	/**
	 * function parse the mysql date type to java date object
	 * 
	 * @name parseMySQLDateFormat
	 * @param String
	 * @return Date
	 * @author Yiheng Tao
	 */
	public static Date parseMySQLDateFormat(String mysqlStr)
			throws ParseException {
		mysqlStr = mysqlStr.substring(0, mysqlStr.length() - 2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(mysqlStr);
	}

	/**
	 * function parse chat message which type of json object to MsgInfo
	 * 
	 * @name parseJsonToReceivedChat
	 * @param JSONObject
	 * @return MsgInfo
	 * @throws JSONException
	 *             , ParseException
	 * @author Yiheng Tao
	 */
	public static MsgInfo parseJsonToReceivedChat(JSONObject obj)
			throws JSONException, ParseException {
		MsgInfo chat = new MsgInfo();
		chat.setType(MsgInfo.MsgType.FROM);
		if (obj.has("chat_id"))
			chat.setChatId(obj.getString("chat_id"));
		if (obj.has("deal_id"))
			chat.setDealId(obj.getString("deal_id"));
		if (obj.has("from_phone"))
			chat.setFromPhone(obj.getString("from_phone"));
		if (obj.has("to_phone"))
			chat.setToPhone(obj.getString("to_phone"));
		if (obj.has("content"))
			chat.setMsg(obj.getString("content"));
		if (obj.has("sub_time"))
			chat.setTime(JSONParser.parseMySQLDateFormat(obj
					.getString("sub_time")));
		return chat;
	}

	/**
	 * function parse the JSONObject to BaseUserInfo
	 * 
	 * @name parseJsonToBaseUserInfo
	 * @param JSONObject
	 * @return BaseUserInfo
	 * @throws JSONException
	 * @author Yiheng Tao
	 */
	public static BaseUserInfo parseJsonToBaseUserInfo(JSONObject obj)
			throws JSONException {
		BaseUserInfo baseUserInfo = new BaseUserInfo();
		if (obj.has("name"))
			baseUserInfo.setRealName(obj.getString("name"));
		if (obj.has("cellphone"))
			baseUserInfo.setPhoneNumber(obj.getString("cellphone"));
		if (obj.has("id"))
			baseUserInfo.setId(obj.getString("id"));
		return baseUserInfo;
	}

	/**
	 * function parse the string to List<JSONObject> and use
	 * parseJsonToBaseUserInfo to change to List<BaseUserInfo>
	 * 
	 * @name parseJsonToBaseUserInfoList
	 * @param String
	 * @return List<BaseUserInfo>
	 * @throws JSONException
	 * @author Yiheng Tao
	 */
	public static List<BaseUserInfo> parseJsonToBaseUserInfoList(String str)
			throws JSONException {
		List<BaseUserInfo> list = new ArrayList<BaseUserInfo>();
		JSONObject json = new JSONObject(str);
		for (int i = 1;; ++i) {
			Integer integer = new Integer(i);
			if (json.has(integer.toString())) {
				JSONObject obj = new JSONObject(json.getString(integer
						.toString()));
				list.add(parseJsonToBaseUserInfo(obj));
			} else
				break;
		}
		return list;
	}
}
