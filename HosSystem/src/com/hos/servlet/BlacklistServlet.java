package com.hos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.dao.BlacklistDao;

@WebServlet("/BlacklistServlet")
public class BlacklistServlet extends BasicServlet {

	private static final long serialVersionUID = -5040155542114801254L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if("getBlacklists".equals(op)) {
			BlacklistDao dao = new BlacklistDao();
			this.send(response, dao.getBlacklists());
		}
	}
}
