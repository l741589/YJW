package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.AddTransBackLogBean;
import com.yjw.bean.AddTransBean;
import com.yjw.bean.Bean;
import com.yjw.bean.ContactBean;
import com.yjw.bean.DealBean;
import com.yjw.bean.TransBean;
import com.yjw.bean.UserBean;
import com.yjw.dao.BaseDAO;
import com.yjw.dao.ContactDAO;
import com.yjw.dao.DealDAO;
import com.yjw.dao.TransDAO;
import com.yjw.dao.UserDAO;
import com.yjw.util.shared.ErrorCode;

public class AddTransAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7909383098611081724L;
	private BaseDAO transDao;
	private UserDAO userDao;
	private BaseDAO dealDao;
	private ContactDAO contactDao;
	//private GenerateTool generateTool;
	/**
	 * Constructor of the object.
	 */
	public AddTransAction() {
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
		
		AddTransBean bean =Bean.Pack(request.getParameter("bean"),AddTransBean.class);
		Set<UserBean> failed=new HashSet<UserBean>();
		Set<UserBean> unreg=new HashSet<UserBean>();
		Set<UserBean> overtrans=new HashSet<UserBean>();
		for (UserBean user:bean.getUsers()){
			int userid = 0;
			ContactBean c=new ContactBean();
			c.setCellphone(user.getCellphone());
			c.setId(bean.getFromid());
			contactDao.add(c);
			if (user.getId()==null){
				UserBean ubean=(UserBean) userDao.getByCellphone(user.getCellphone());
				if (ubean!=null){
					userid=ubean.getId();
				}
			}else userid = user.getId();
			
			if (userid==0) {
				unreg.add(user);
				continue;
			}
			if (bean.getTranses()!=null){
				for (TransBean trans:bean.getTranses()){
					TransBean t=new TransBean();
					DealBean d=(DealBean)dealDao.get(trans.getDeal_id());
					if (d==null) { failed.add(user); continue;}
					if (trans.getTimes()>=d.getMaxtrans()){	overtrans.add(user);continue; }
					t.setFrom_id(bean.getFromid());
					t.setDeal_id(d.getId());
					t.setTo_id(userid);
					if (!d.getOwner_id().equals(t.getFrom_id()))
						t.setTimes(trans.getTimes()+1);
					else
						t.setConfirmed(1);
					if (transDao.add(t)==-1) failed.add(user);
				}
			}
			if (bean.getDeals()!=null){
				for (DealBean deals:bean.getDeals()){
					TransBean t=new TransBean();
					t.setFrom_id(bean.getFromid());
					t.setDeal_id(deals.getId());
					t.setTo_id(userid);
					t.setConfirmed(1);
					if (transDao.add(t)==-1) failed.add(user);
				}
			}
		}
		if (failed.size()==0&&unreg.size()==0&&overtrans.size()==0){
			out.print(ErrorCode.E_SUCCESS);
		}else{
			out.print(ErrorCode.E_ADD_TRANS_FAILED);
			out.print("&");
			AddTransBackLogBean blog=new AddTransBackLogBean();
			UserBean[] arFailed = null;
			UserBean[] arUnreg = null;
			UserBean[] arOvertrans = null;
			if (failed.size()!=0){arFailed=new UserBean[failed.size()];failed.toArray(arFailed);}
			if (unreg.size()!=0){arUnreg=new UserBean[unreg.size()];unreg.toArray(arUnreg);}
			if (overtrans.size()!=0) {arOvertrans=new UserBean[overtrans.size()];overtrans.toArray(arOvertrans);}
			blog.setFailed(arFailed);
			blog.setNot_reg(arUnreg);
			blog.setOvertrans(arOvertrans);
			out.print(blog);
		}
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
		transDao=new TransDAO();
		userDao=new UserDAO(); 
		dealDao=new DealDAO();
		contactDao=new ContactDAO();
	}
}
