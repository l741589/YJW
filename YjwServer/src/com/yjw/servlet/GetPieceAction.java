package com.yjw.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.Bean;
import com.yjw.bean.PieceBean;
import com.yjw.dao.PieceDAO;
import com.yjw.util.Util;

public class GetPieceAction extends HttpServlet {
	
	private static final long serialVersionUID = 403532287951289776L;
	private PieceDAO pieceDao;

	public GetPieceAction() {
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
		OutputStream out = response.getOutputStream();
		PieceBean bean=Bean.Pack(request.getParameter("bean"),PieceBean.class);
		int id=bean.getId();
		int pid=bean.getPid();
		PieceBean pb=(PieceBean)pieceDao.get(id, pid);
		if (pb!=null){
			byte[] data=pb.getData();
			out.write(Util.int2bytes(data.length));
			out.write(data);
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		pieceDao=new PieceDAO();
	}

}
