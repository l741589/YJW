package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.Bean;
import com.yjw.bean.RegisterBean;
import com.yjw.bean.UserBean;
import com.yjw.dao.RegisterDAO;
import com.yjw.util.shared.ErrorCode;

public class RegisterAction extends HttpServlet {

	private static final long serialVersionUID = 8674625371525628805L;
	//private JdbcTemplate jdbcTemplate;
	private RegisterDAO registerDAO;
	/**
	 * Constructor of the object.
	 */
	public RegisterAction() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String msg = "";
		
		
		RegisterBean rbean = Bean.Pack(request.getParameter("bean"),RegisterBean.class);
		String sid = rbean.getSid();
		String code = rbean.getValidateCode();
		//if(this.registerDAO.validate(sid, code)){
		if (true){
			UserBean u=rbean.to(UserBean.class);
			if(this.registerDAO.register(u)){
				msg = ErrorCode.E_SUCCESS.toString();
			}else{
				if (registerDAO.isCellphoneDuplicate(u.getCellphone()))
					msg = ErrorCode.E_DUBLICATE_ID.toString();
				else if (registerDAO.isNameDuplicate(u.getName()))
					msg = ErrorCode.E_DUBLICATE_NAME.toString();
				else
					msg = ErrorCode.E_FAILED.toString();
			}
		}else{
			msg = ErrorCode.E_INVALIDATE_FAILED.toString();
		}
		out.print(msg);
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		//this.jdbcTemplate = new GetJdbcTemplate().getJtl();
		this.registerDAO = new RegisterDAO();
	}

}
