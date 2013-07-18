package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.Bean;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.dao.BaseDAO;
import com.yjw.dao.DealDAO;
import com.yjw.dao.TransDAO;
import com.yjw.util.shared.ErrorCode;

public class AddDealAction extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1698747204414818564L;
	private BaseDAO dealDao;
	private BaseDAO transDao;
	//private GenerateTool generateTool;
	/**
	 * Constructor of the object.
	 */
	public AddDealAction() {
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
		//ServletOutputStream out = response.getOutputStream();
		DealBean d = Bean.Pack(request.getParameter("bean"),DealBean.class);
		int deal_id;
		if ((deal_id=dealDao.add(d))>0){
			out.print(ErrorCode.E_SUCCESS);
			out.print("&");
			TransBean t=new TransBean();
			t.setDeal_id(deal_id);
			t.setFrom_id(d.getOwner_id());
			t.setTo_id(d.getOwner_id());
			t.setConfirmed(1);
			if(transDao.add(t)>0){
				out.print(ErrorCode.E_SUCCESS);
			}else{
				out.print(ErrorCode.E_ADD_TRANS_FAILED);
			}
		}else{
			out.print(ErrorCode.E_ADD_DEAL_FAILED);
		}
		out.print("&");
		//添加完业务后添加相应的分享
		
		
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
		// Put your code here
		dealDao=new DealDAO();
		transDao=new TransDAO();
	}
	
	/*public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
	}*/

}
