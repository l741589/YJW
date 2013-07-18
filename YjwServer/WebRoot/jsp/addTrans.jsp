<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//request.getSession().setAttribute("deals",deals.value);
String oldDeals= (String)request.getSession().getAttribute("deals");
//String str_deals=oldDeals+request.getparameter("bean");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addTrans.jsp' starting page</title>
    
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
		fromid:<input type="text" name="fromid"/><br/>
		users：<input type="hidden" name="users"/><a href="jsp/fillUsers.jsp">Fill</a><br/>
		deals:<input type="hidden" name="deals" value=""/><br/> <a href="jsp/fillDeals.jsp">Fill</a><br/>
		transes：<input type="hidden" name="transes"/><br/> <a href="jsp/fillTranses.jsp">Fill</a><br/>
		<input type="hidden" name="_class" value="AddTransBean"/>
		<input type="hidden" name="_action" value="AddTransAction"/>
 		<input type="submit">
	</form></body>
</html>
