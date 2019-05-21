package com.hos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hos.util.KeyUtil;
import com.hos.util.StringUtil;
import com.yc.dao.OrderDao;

@WebServlet("/OrderServlet")
public class OrderServlet extends BasicServlet {

	private static final long serialVersionUID = 7097984356166868573L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if("getCount".equals(op)) {
			String sid = request.getParameter("sid");
			OrderDao dao = new OrderDao();
			this.send(response, dao.getCount(sid));
		}
		else if("addOrder".equals(op)) {
			String usid = String.valueOf(request.getSession().getAttribute(KeyUtil.USERID));
			String sid = request.getParameter("sid");
			if(!StringUtil.isNull(usid, sid)) {
				OrderDao dao = new OrderDao();
				this.send(response, dao.addOrder(usid, sid));
			} else {
				this.send(response, 0);
			}
		}
		else if("getAllOrders".equals(op)) {
			OrderDao dao = new OrderDao();
			this.send(response, dao.getAllOrders());
		}
		else if("getOrderShows".equals(op)) {
			Object usid = request.getSession().getAttribute(KeyUtil.USERID);
			if(usid != null) {
				OrderDao dao = new OrderDao();
				this.send(response, dao.getOrderShows(String.valueOf(usid)));;
			}
			
		}
		else if("getOrderBackShows".equals(op)) {
			OrderDao dao = new OrderDao();
			this.send(response, dao.getOrderBackShows());;
		}
	}
}
