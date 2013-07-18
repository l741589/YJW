package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.ValidationBean;
import com.yjw.dao.RegisterDAO;
import com.yjw.util.GenerateTool;
import com.yjw.util.shared.ErrorCode;

public class GetValidateCodeAction extends HttpServlet {
	
	private static final long serialVersionUID = 3238815126905353714L;
	private RegisterDAO registerDao;
	private GenerateTool gnerateTool;
	/**
	 * Constructor of the object.
	 */
	public GetValidateCodeAction() {
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
		String msg = "";

		// 获取需要的信息
		String cellphone = request.getParameter("cellphone");
		if (this.registerDao.isCellphoneDuplicate(cellphone)) {
			msg = ErrorCode.E_DUBLICATE_ID.toString();
		} else {
			
			String sid = this.gnerateTool.generateSid();
			String validateString = this.gnerateTool.genValidateString();
			
			ValidationBean bean = new ValidationBean();
			bean.setSid(sid);
			bean.setValidation_code(validateString);

			
		//	if(proxy.register(userBean)){
				//加入验证信息
			
			if(this.registerDao.insertValidateCode(bean)){
				msg = ErrorCode.E_SUCCESS + "&"+sid+"&"+validateString;
				// TODO
				//msg = ErrorCode.E_SUCCESS + "&"+sid;
				//发送短信
				System.out.println(msg);
			}else{
				msg = ErrorCode.E_FAIL_TO_GET_VALIDATE_INFO.toString();
			}
		}
		out.print(msg);
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
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	@Override
	public void init() throws ServletException {
		this.registerDao = new RegisterDAO();
		this.gnerateTool = new GenerateTool();
	}

}
