package com.yjw.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.PieceBean;
import com.yjw.dao.PieceDAO;
import com.yjw.util.Util;
import com.yjw.util.shared.ErrorCode;

public class AddPieceAction extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8800479680941314586L;
	private PieceDAO pieceDao;

	public AddPieceAction() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("image/png");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		InputStream is=request.getInputStream();
		PrintWriter out = response.getWriter();
		byte[] b4=new byte[4];
		is.read(b4);
		int len=Util.bytes2int(b4);
		is.read(b4);
		int id=Util.bytes2int(b4);
		is.read(b4);
		int pid=Util.bytes2int(b4);
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		byte[] bs=new byte[len-8];
		int recv=0;
		while(recv<len-8){
			int l=is.read(bs);
			recv+=l;
			os.write(bs,0,l);
			System.out.println("Received "+recv+"/"+(len-8)+" bytes");
		}
		bs=os.toByteArray();
		PieceBean bean=new PieceBean();
		bean.setId(id);
		bean.setPid(pid);
		bean.setData(bs);
		if (pieceDao.add(bean)!=-1){
			out.print(ErrorCode.E_SUCCESS);
		}else{
			out.print(ErrorCode.E_ADD_PIECE_FAILED);
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
