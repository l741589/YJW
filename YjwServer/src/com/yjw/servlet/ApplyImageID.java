package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.ArrayBean;
import com.yjw.bean.Bean;
import com.yjw.dao.ImgDAO;
import com.yjw.util.shared.ErrorCode;

public class ApplyImageID extends HttpServlet {

	private static final long serialVersionUID = -9108840348707145530L;
	private ImgDAO imgDao;

	public ApplyImageID() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		ArrayBean<?> arbean=Bean.Pack(request.getParameter("bean"),ArrayBean.class);
		Object[] beans=arbean.getData();
		String s=ErrorCode.E_SUCCESS.toString();
		try{
			for (Object bean:beans){
				int id=imgDao.add((Bean)bean);
				if (id<=0) throw new Exception();
				Bean ret=imgDao.get(id);
				if (ret==null) throw new Exception();
				s+="&"+ret;
			}
			out.print(s);
		}catch(Exception e){
			out.print(ErrorCode.E_APPLY_IMG_ID_FAILED);
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public void init() throws ServletException {
		imgDao=new ImgDAO();
	}

}
