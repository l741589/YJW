package com.yjw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjw.bean.ArrayBean;
import com.yjw.dao.CtrlDAO;
import com.yjw.util.shared.ErrorCode;

public class ControlAction extends HttpServlet {
	private static final long serialVersionUID = 5945440993567104824L;
	
	private static Hashtable<Integer,Thread> mutex=new Hashtable<Integer, Thread>();
	private CtrlDAO ctrlDao;
	private static final int TIME_WAIT = 30 * 60 * 1000;
	
	/**通过此函数对指定用户进行消息推送，推送内容有yjw_ctrl表决定
	 * @param id 指定用户 id
	 * @return 目标用户是否在线，准确性可能有最大30分钟的延迟
	 * */
	public static synchronized boolean push(int id){
		Thread thread=mutex.get(id);
		if (thread==null) {
			System.out.println("Target user is offline");
			return false;
		}
		synchronized (thread) {
			thread.notify();
		}
		return true;
	}

	public ControlAction() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	@SuppressWarnings("unchecked")
	private String getCodes(int id){
		ArrayBean<Integer> bean=(ArrayBean<Integer>)ctrlDao.get(id);
		if (bean==null) return null;
		Integer[] is=bean.getData();
		if (is==null||is.length==0) return null;
		String s="";
		for (Integer x:is) s+="&"+x;
		return s;
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Thread thread=Thread.currentThread();
		int id=Integer.valueOf(request.getParameter("bean"));
		mutex.put(id,thread);
		System.out.println("Start Control user "+id);
		String ret=getCodes(id);
		if (ret==null){
			synchronized(thread){
				try {
					thread.wait(1800000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ret=getCodes(id);
		}
		if (ret==null)out.print(ErrorCode.E_NULL_CTRL);
		out.print(ErrorCode.E_SUCCESS+ret);
		out.flush();
		out.close();
		System.out.println("Stop Control user "+id);
		mutex.remove(thread);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		ctrlDao=new CtrlDAO();
	}

}
