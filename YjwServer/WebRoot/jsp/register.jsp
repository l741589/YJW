<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'register.jsp' starting page</title>
    
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
		name:<input type="text" name="name"/><br/>
		cellphone:<input type="text" name="cellphone"/><br/>
		password:<input type="text" name="password"/><br/>
		email:<input type="text" name="email"/><br/>
		<input type="hidden" name="_class" value="RegisterBean"/>
		<input type="hidden" name="_action" value="RegisterAction"/>
		<input type="submit">
	</form>
  </body>
</html>
