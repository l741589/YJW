package com.yjw.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.Bean;
import com.yjw.dao.ContactDAO;
import com.yjw.util.shared.ErrorCode;


public class SyncUserAction extends HttpServlet {
	private static final long serialVersionUID = -1054157151739333085L;
	private ContactDAO contactDao;

	public SyncUserAction() {
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
		Bean bean=contactDao.get(id);
		if (bean==null){
			out.print(ErrorCode.E_SYNC_USER_FAILED);
		}else{
			out.print(ErrorCode.E_SUCCESS);
			out.print("&");
			out.print(bean);
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public void init() throws ServletException {
		contactDao=new ContactDAO();
	}

}
