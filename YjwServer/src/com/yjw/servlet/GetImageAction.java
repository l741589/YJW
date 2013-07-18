package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.ImageBean;
import com.yjw.dao.ImgDAO;
import com.yjw.util.shared.ErrorCode;

public class GetImageAction extends HttpServlet {
	
	private static final long serialVersionUID = -2782661862520448000L;
	private ImgDAO imgDao;

	public GetImageAction() {
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
		int id=Integer.valueOf(request.getParameter("bean"));
		
		ImageBean bean=(ImageBean)imgDao.get(id);
		if (bean!=null){
			if (bean.getData().getDone().equals(bean.getData().getLength())){
				out.print(ErrorCode.E_SUCCESS);
				out.print("&");
				out.print(bean);	
			}else{
				out.print(ErrorCode.E_DATA_NOT_DONE);
			}
			
		}else{
			out.print(ErrorCode.E_GET_IMAGE_FAILED);
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
