package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.Bean;

public class ResultAction extends HttpServlet {

	public ResultAction() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	private String space(int width){
		//return "<p style:text-indent="+20*width+"px>";
		//return "<span style=\"width:"+(100*width)+"px;\">&nbsp;</span>";
		//String s="";
		//while (width-->0) s+="\t";
		//return s;
		return "<p style=\"margin-left:"+20*width+"px;margin-top:0px;margin-bottom:0px;\">";
	}
	
	private void output(PrintWriter out,Bean bean,int level,String field){
		int lv=level+1;
		if (bean.isBean()){
			Class<? extends Bean> cls=bean.getClass();
			out.print(space(level)+"<b>"+cls.getSimpleName()+"</b></p><br/>");
		
			Map<String,Object> map=bean.toMap();
			Set<String> keys=map.keySet();
			for (String key:keys){
				Object o=map.get(key);
				Bean b=Bean.Pack(o);
				output(out,b,lv,key);				
			}
		} else {
			String s=Bean.cast(bean.toObject());
			if (s.charAt(0)=='{'){
			List<Bean> list=Bean.strToArr(s);
			Integer i=0;
			for (Bean e:list) output(out,e,lv,(i++).toString());				
			}else{
				out.print(space(lv)+"<b>"+field+"</b> "+bean+"</p><br/>");
			} 
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Map map=request.getParameterMap();
		Object obj=request.getParameter("text");
		String text=request.getParameter("text");
		System.out.println(text);
		List<String> req=Bean.split(text,'&');
	  	for (String value:req){
	  		Bean bean=Bean.Pack(value);
	  		if (bean.isBean()){
	  			output(out,bean,0,"");
	  		}else{
	  			out.print(space(0)+value+"</p><br/>");
	  		}	  		
	  	}
	  

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
