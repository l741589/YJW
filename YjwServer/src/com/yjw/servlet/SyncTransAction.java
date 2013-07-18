package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.Bean;
import com.yjw.bean.GetInfoBean;
import com.yjw.dao.BaseDAO;
import com.yjw.dao.TransDAO;
import com.yjw.util.shared.ErrorCode;

public class SyncTransAction extends HttpServlet {
	
	private static final long serialVersionUID = -1947173821210714373L;
	private BaseDAO transDao;

	/**
	 * Constructor of the object.
	 */
	public SyncTransAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		GetInfoBean bean=Bean.Pack(request.getParameter("bean"),GetInfoBean.class);
		List<Integer> ints=transDao.sync(bean);
		if (ints.size()==0){
			out.print(ErrorCode.E_NULL_DEAL);
		}else{
			out.print(ErrorCode.E_SUCCESS);
			for (Integer i:ints){
				out.print("&");
				out.print(i);
			}
		}
		out.flush();
		out.close();
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		transDao=new TransDAO();
	}

}
