<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addDeal.jsp' starting page</title>
    
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
         <form action="jsp/PackerJsp.jsp" method="post">
			owner_id:<input type="text" name="owner_id"/><br/>
			title：<input type="text" name="title"/><br/>
			content:<input type="text" name="content"/><br/>
			fee：<input type="text" name="fee"/><br/>
			expire_date：<input type="text" name="expire_date"/><br/>
			<input type="hidden" name="_class" value="DealBean"/>
			<input type="hidden" name="_action" value="AddDealAction"/>
 			<input type="submit">
  </form></body>
</html>
