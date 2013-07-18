<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'getDeal.jsp' starting page</title>
    
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
  	<script type="text/javascript">
  		function add(){
  			var psize=document.getElementById("size");
  			var size=parseInt(psize.getAttribute("value"));
  			var d=document.getElementById("ids");
  			var np=document.createElement("input");
  			np.setAttribute("name",""+size);
  			np.setAttribute("type","text");
  			np.setAttribute("id",""+size);
  			d.appendChild(np);
  			psize.setAttribute("value",""+(size+1));
  			d.appendChild(document.createElement("br"));
  		}
  	</script>
    <form action="jsp/PackerJsp.jsp" method="post">
    	<input type="hidden" name="size" value="0" id="size"/>
    	Phone Numbers:<br/>
    	<div id="ids"></div>
		<input type="hidden" name="_action" value="GetUserByPhoneAction"/>
 		<input type="submit">
	</form>
	<button onclick="add()">add</button>
  </body>
</html>
