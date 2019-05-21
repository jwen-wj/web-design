package com.hos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hos.util.StringUtil;
import com.yc.dao.ScheduDao;

@WebServlet("/ScheduServlet")
public class ScheduServlet extends BasicServlet {

	private static final long serialVersionUID = 4882029135742901385L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if("getSchedu".equals(op)) {
			String did = request.getParameter("did");
			String sdate = request.getParameter("sdate");
			String timetype = request.getParameter("timetype");
			if(!StringUtil.isNull(did, sdate, timetype)) {
				ScheduDao dao = new ScheduDao();
				this.send(response, dao.getSchedu(did, sdate, timetype));
			}
		}
		else if("getSchedus".equals(op)) {
			String did = request.getParameter("did");
			String sdate = request.getParameter("sdate");
			if(!StringUtil.isNull(did, sdate)) {
				ScheduDao dao = new ScheduDao();
				this.send(response, dao.getSchedus(did, sdate));
			}
		}
		else if("getScheduBySid".equals(op)) {
			String sid = request.getParameter("sid");
			if(!StringUtil.isNull(sid)) {
				ScheduDao dao = new ScheduDao();
				this.send(response, dao.getScheduBySid(sid));
			}
		}
		else if("getAllSchedus".equals(op)) {
			ScheduDao dao = new ScheduDao();
			this.send(response, dao.getAllSchedus());
		}
	}

}
