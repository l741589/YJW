package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.AddImageBean;
import com.yjw.bean.Bean;
import com.yjw.bean.DataBean;
import com.yjw.bean.ImageBean;
import com.yjw.dao.DataDAO;
import com.yjw.dao.ImgDAO;
import com.yjw.util.shared.ErrorCode;

public class AddImageAction extends HttpServlet {

	private static final long serialVersionUID = -8376830747493242468L;
	private ImgDAO imgDao;
	private DataDAO dataDao;
	
	public AddImageAction() {
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
		AddImageBean bean=Bean.Pack(request.getParameter("bean"),AddImageBean.class);
		if (bean!=null){			
			DataBean db=bean.to(DataBean.class);
			db.setId(null);
			db.setMark("IMG");
			int did=dataDao.add(db);
			if (did>0){
				ImageBean ib=bean.to(ImageBean.class);
				ib.setDid(did);
				if (imgDao.update(ib)>=0){
					out.print(ErrorCode.E_SUCCESS);
					out.print("&");
					out.print(did);
				}else{
					out.print(ErrorCode.E_ADD_IMGAE_FAILED);
				}
			}else{
				out.print(ErrorCode.E_ADD_IMGAE_FAILED);
			}
		}else{
			out.print(ErrorCode.E_ADD_IMGAE_FAILED);
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
		dataDao=new DataDAO();
	}

}
