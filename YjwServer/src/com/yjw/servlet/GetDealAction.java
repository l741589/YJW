package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.Bean;
import com.yjw.dao.BaseDAO;
import com.yjw.dao.DealDAO;
import com.yjw.util.shared.ErrorCode;

public class GetDealAction extends HttpServlet {
	
	private static final long serialVersionUID = 7795676210198803670L;
	private BaseDAO dealDao;
	/**
	 * Constructor of the object.
	 */
	public GetDealAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	@Override
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
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		 
		int size=Integer.valueOf(request.getParameter("size"));
		out.print(ErrorCode.E_SUCCESS);
		for (Integer i=0;i<size;++i){
			int id = Integer.valueOf(request.getParameter(i.toString()));
			Bean bean=dealDao.get(id);
			if (bean!=null){
				out.print("&");			
				out.print(bean);
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
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	@Override
	public void init() throws ServletException {
		dealDao=new DealDAO();
		super.init();
	}
}
