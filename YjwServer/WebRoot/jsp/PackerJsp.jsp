<%@page import="com.lowagie.text.Document"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.yjw.bean.Bean"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<title>My JSP 'PackerJsp.jsp' starting page</title>
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
  	<%
  	Map<String,String[]> rawreq=request.getParameterMap();
  	Map<String,String> req=new HashMap<String,String>();
  	Set<String> keys=rawreq.keySet();
	String sreq="";
	boolean start=true;
  	for (String key:keys){
  		String value=rawreq.get(key)[0];
  		req.put(key,value);
  		if (start) start=false; else sreq+="&";
  		sreq+=key+"="+value;
  	}	
  	
  	String action="/YjwServer/"+req.get("_action");
  	String classname=req.get("_class");
  	if (classname!=null){  
  		try{  		 		
  			Class<?> _cls=Class.forName("com.yjw.bean."+classname);
  			Class<? extends Bean> cls=(Class<? extends Bean>)_cls;
  			Bean bean=Bean.Pack(req, cls);
  			if (start) start=false; else sreq+="&";
  			sreq+="bean="+bean;
  		}catch(ClassNotFoundException e){
  		}catch(Exception e){
  			out.println("Illegal parameters");
  		}	
  	}
  	System.out.println(sreq);
  	%>
  	<script type="text/javascript">
  		function createXMLHTTP(){
  			if (window.XMLHttpRequest)
			{// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp=new XMLHttpRequest();
			}
			else
			{// code for IE6, IE5
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			return xmlhttp;
  		}
		var xmlhttp;
		var xmlhttp2;
		var text;
		xmlhttp=createXMLHTTP();
		xmlhttp.onreadystatechange=function()
  		{
  			if (xmlhttp.readyState==4 && xmlhttp.status==200)
    		{
    			var text=xmlhttp.responseText;
    			text=encodeURIComponent(text); 
    			//document.getElementById("result").innerHTML=text;   
    			xmlhttp2=createXMLHTTP();
    			xmlhttp2.onreadystatechange=function(){
    				if (xmlhttp.readyState==4 && xmlhttp.status==200)
    				{
    					var text2=xmlhttp2.responseText;
    					document.getElementById("result").innerHTML=text2;
    				}
    			}
    			xmlhttp2.open("POST","ResultAction",true);
				xmlhttp2.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				document.getElementById("result").innerHTML=text; 
				xmlhttp2.send("text="+text);
    		}
  		}
  		xmlhttp.open("POST","<%=action%>",true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("<%=sreq%>");
  	</script>
    <div id="result"></div>
  </body>
</html>
