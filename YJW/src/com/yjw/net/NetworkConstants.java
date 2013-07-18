package com.yjw.net;

public class NetworkConstants {
	//public static String SERVER_URL = "http://sselab.tongji.edu.cn/";//"http://192.168.1.110:8080/";//"http://sselab.tongji.edu.cn/";//"http://10.0.2.2:8080/";//"http://sselab.tongji.edu.cn/";
	//public static String SERVER_URL_HTTPS = "https://192.168.71.1:8443/";
	//public static String SERVER_URL_HTTPS = "https://221.239.235.181:8443/";
	public static String SERVER_URL_HTTPS = "https://192.168.1.100:8443/";
	public static String SERVER_URL = "http://192.168.71.1:8080/";
	//public static String SERVER_URL = "http://222.69.212.95:8080/";
	//public static String SERVER_URL = "http://apache:secret@ec2-54-251-222-148.ap-southeast-1.compute.amazonaws.com/git/yjw/";
	public static String SERVER_APPNAME = "YjwServer";

	public static final String URL_CONTROL = SERVER_APPNAME
			+ "/ControlAction";
	
	public static final String URL_REGISTER = SERVER_APPNAME
			+ "/RegisterAction";
	public static final String URL_VALIDATECODE = SERVER_APPNAME
			+ "/GetValidateCodeAction";
	public static final String URL_LOGIN = SERVER_APPNAME
			+ "/LoginAction";
	public static final String URL_CHAT = SERVER_APPNAME 
			+ "/ChatAction";
	
	public static final String URL_SYNCUSER = SERVER_APPNAME
			+ "/SyncUserAction";
	public static final String URL_GETUSER = SERVER_APPNAME
			+ "/GetUserAction";
	public static final String URL_GETUSERBYPHONE = SERVER_APPNAME
			+ "/GetUserByPhoneAction";
	
	public static final String URL_SYNCTRANS = SERVER_APPNAME
			+ "/SyncTransAction";
	public static final String URL_GETTRANS = SERVER_APPNAME
			+ "/GetTransAction";
	public static final String URL_ADDTRANS = SERVER_APPNAME
			+ "/AddTransAction";
	public static final String URL_DELTRANS = SERVER_APPNAME
			+ "/DelTransAction";
	public static final String URL_CONFTRANS = SERVER_APPNAME
			+ "/ConfTransAction";
	
	public static final String URL_SYNCDEAL = SERVER_APPNAME
			+ "/SyncDealAction";	
	public static final String URL_ADDDEAL = SERVER_APPNAME
			+ "/AddDealAction";
	public static final String URL_DELDEAL = SERVER_APPNAME
			+ "/DelDealAction";
	public static final String URL_GETDEAL = SERVER_APPNAME
			+ "/GetDealAction";
	
	public static final String URL_APPLYIMGID = SERVER_APPNAME
			+ "/ApplyImageID";
	public static final String URL_ADDIMAGE = SERVER_APPNAME
			+ "/AddImageAction";
	public static final String URL_GETIMAGE = SERVER_APPNAME
			+ "/GetImageAction";
	
	
	public static final String URL_FORWARD = SERVER_APPNAME
			+ "/ForwardDealAction";	
	public static final String URL_SHAREDUSER = SERVER_APPNAME
			+ "/GetDealSharedUsersAction";
	public static final String URL_ACCEPT = SERVER_APPNAME
			+ "/AcceptDealAction";
	public static final String URL_DECLINE = SERVER_APPNAME
			+ "/DeclineDealAction";
	
	
//	public static String URL_REGISTERVALIDATE = SERVER_APPNAME
//			+ "/RegisterValidateAction";
//	public static String URL_RESENDVALIDATE = SERVER_APPNAME
//			+ "/ResendValidateMsgAction";
//	public static String URL_FINISHREGISTER = SERVER_APPNAME
//			+ "/FinishRegisterAction";
	public static final String URL_CHECKVERSION = SERVER_APPNAME
			+ "/OutputVersionAction";
	public static final String URL_FETCHBALANCE = SERVER_APPNAME
			+ "/OutputBalanceAction";
	public static final String URL_GETPIECE = SERVER_APPNAME
			+ "/GetPieceAction";
	public static final String URL_ADDPIECE = SERVER_APPNAME
			+ "/AddPieceAction";
	
	
	
	
}
