/*
 * Created By Yiheng Tao
 * YJWMessage.java
 * 
 * This class defined some messages will be used in sending messages to handler
 * each of will has a number which should be equal in both sending side and receiving side
 * please add more messages if needed
 * 
 */

package com.yjw.util;

public enum YJWMessage {
	YJWMESSAGE_NULL,
	/* messages used in PushMsgThread.java and DealReplyPageActivity.java */
	SEND_MESSAGE_SUCCESS,
	SEND_MESSAGE_FAILURE,

	/* messages used in PullMsgThread.java and YJWActivity.java */
	GET_MESSAGE_SUCCESS,
	GET_MESSAGE_NULL,
	GET_MESSAGE_FAILURE,

	/* messages used in GetSharedUserThread.java and DealDetailPageActivity.java */
	GET_SHARED_USER_SUCCESS,
	GET_SHARED_USER_NONE,
	GET_SHARED_USER_FAILURE,

	CONFIRM_ACCEPT_SUCCESS,
	CONFIRM_ACCEPT_FAILURE,
	CONFIRM_DECLINE_SUCCESS,
	CONFIRM_DECLINE_FAILURE,
	
	/* messages used in Register */
	REGISTER_SUCCESS,
	REGISTER_FAILURE,
	REGISTER_GETVALIDATECODE,

	/* fetch balance */
	FETCH_BALANCE_SUCCESS,
	FETCH_BALANCE_FAILURE,
	
	/* check update */
	NEW_UPDATE,
	
	/* network messages*/
	NET_FAIL_RECONNECT,
	NET_FAIL_NORECONNECT,
	
	/* message used in Login */
	LOGIN_SUCCESS,
	LOGIN_FAILED,
	
	/* message used in deal*/
	SYNC_DEAL_SUCCESS,
	SYNC_DEAL_FAILED,
	ADD_DEAL_SUCCESS,
	ADD_DEAL_FAILED,
	DEL_DEAL_SUCCESS,
	DEL_DEAL_FAILED,
	GET_DEAL_SUCCESS,
	GET_DEAL_FAILED,
	
	/* message used in trans*/
	GET_TRANS_SUCCESS,
	GET_TRANS_FAILED,
	ADD_TRANS_SUCCESS,
	ADD_TRANS_FAILED,
	DEL_TRANS_SUCCESS,
	DEL_TRANS_FAILED,
	SYNC_TRANS_SUCCESS,
	SYNC_TRANS_FAILED,
	CONF_TRANS_SUCCESS,
	CONF_TRANS_FAILED,
	CONF_TRANS_NOT_ENOUGH_BALANCE,
	
	/* message used in buffer thread */
	BUFFER_USER,
	BUFFER_DEAL,
	BUFFER_CHAT,
	
	/*message used in user thread */
	SYNC_USER_SUCCESS,
	SYNC_USER_FAILED, 
	
	/*message used in control thread */
	CONTROL_RECV, 
	/*message used in init table thread */
	INIT_TABLE_DONE,
	/*message used in gen text thread */
	GEN_TEXT_DONE,
	/*message used in apply image id thread*/
	APPLY_IMG_ID_SUCCESS,
	APPLY_IMG_ID_FAILED,
	/*message used in add image thread*/
	ADD_IMAGE_SUCCESS,
	ADD_IMAGE_FAILED,
	GET_IMAGE_SUCCESS,
	GET_IMAGE_FAILED,
	
	ADD_BIGOBJ_SUCCESS, 
	ADD_BIGOBJ_FIALED,
	GET_BIGOBJ_SUCCESS, 
	GET_BIGOBJ_FIALED,
}
