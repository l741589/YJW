<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  	<!-- a href="jsp/addDeal.jsp">AddDeal</a><br-->
    <!-- a href="jsp/addImage.jsp">AddImage</a><br-->
    <a href="jsp/addDeal.jsp">AddDeal</a><br>
    <!-- a href="jsp/addTrans.jsp">AddTrans</a><br -->
   	<!-- a href="jsp/applyImageID.jsp">ApplyImageID</a><br-->
   	<!-- a href="jsp/confTrans.jsp">ConfTrans</a><br-->
   	<!-- a href="jsp/control.jsp">Control</a><br-->
   	<a href="jsp/delDeal.jsp">DelDeal</a><br>
    <a href="jsp/delTrans.jsp">DelTrans</a><br>
   
   	<a href="jsp/getDeal.jsp">GetDeal</a><br>
    <!--  a href="jsp/getImage.jsp">GetImage</a><br-->
    <a href="jsp/getTrans.jsp">GetTrans</a><br>
    <a href="jsp/getUser.jsp">GetUser</a><br>
    <!--a href="jsp/getPiece.jsp">GetPiece</a><br-->
    <a href="jsp/getUserByPhone.jsp">GetUserByPhone</a><br>
    <a href="jsp/getValidateCode.jsp">GetValidateCode</a><br>
    <a href="jsp/login.jsp">Login</a><br>
	<!-- a href="jsp/outputVersion.jsp">OutputVersion</a><br-->
	<!-- a href="jsp/register.jsp">Register</a><br-->
	<a href="jsp/syncDeal.jsp">SyncDeal</a><br>
	<a href="jsp/syncTrans.jsp">SyncTrans</a><br>
	<a href="jsp/syncUser.jsp">SyncUser</a><br>
	<!-- a href="jsp/updateDeal.jsp">UpdateDeal</a><br-->
  </body>
</html>
