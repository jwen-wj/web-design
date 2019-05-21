package com.hos.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.hos.util.KeyUtil;
import com.hos.util.StringUtil;
import com.yc.dao.DoctorDao;

@WebServlet("/DoctorServlet")
public class DoctorServlet extends BasicServlet {

	private static final long serialVersionUID = 6519505899637729473L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if("getDoctors".equals(op)) {
			String kid = request.getParameter("kid");
			String sdate = request.getParameter("sdate");
			String timetype = request.getParameter("timetype");
			if(!StringUtil.isNull(kid)) {
				DoctorDao dao = new DoctorDao();
				this.send(response, dao.getDoctors(kid, sdate, timetype));
			}
		}
		else if("getDoctor".equals(op)) {
			String did = request.getParameter("did");
			if(!StringUtil.isNull(did)) {
				DoctorDao dao = new DoctorDao();
				this.send(response, dao.getDoctor(did));
			}
		}
		else if("getDoctorsByKid".equals(op)) {
			String kid = request.getParameter("kid");
			if(!StringUtil.isNull(kid)) {
				DoctorDao dao = new DoctorDao();
				this.send(response, dao.getDoctorsByKid(kid));
			}
		}
		else if("getHotDoctors".equals(op)) {
			DoctorDao dao = new DoctorDao();
			this.send(response, dao.getHotDoctors());
		}
		else if("getAllDoctors".equals(op)) {
			DoctorDao dao = new DoctorDao();
			this.send(response, dao.getAllDoctors());
		}
		else if("updateDoctor".equals(op)) {
			String did = request.getParameter("did");
			String kid = request.getParameter("kid");
			String dname = request.getParameter("dname");
			String msg = request.getParameter("msg");
			String pic = request.getParameter("pic");
			if(!StringUtil.isNull(did, kid, dname, msg, pic)) {
				DoctorDao dao = new DoctorDao();
				this.send(response, dao.updateDoctor(did, kid, dname, msg, pic));
			} else {
				this.send(response, 0);
			}
		}
		else if("login".equals(op)) {
			String did = request.getParameter("did");
			String dname = request.getParameter("dname");
			int result = 0;
			if(!StringUtil.isNull(did, dname)) {
				DoctorDao dao = new DoctorDao();
				result = dao.login(did, dname);
				if(result > 0) {
					request.getSession().setAttribute(KeyUtil.DOCTORID, did);
				}
			}
			this.send(response, result);
		}
		else if("getDoctorShows".equals(op)) {
			DoctorDao dao = new DoctorDao();
			this.send(response, dao.getDoctorShows());
		}
		else if("getDoctorsByPage".equals(op)) {
			String page = request.getParameter("page");
			String limit = request.getParameter("limit");
			Map<String, Object> result = new HashMap<>();
			if(!StringUtil.isNull(page, limit)) {
				DoctorDao dao = new DoctorDao();
				result.put("code", 0);
				result.put("count", dao.getTotal());
				result.put("data", dao.getDoctorsByPage(Integer.parseInt(page), Integer.parseInt(limit)));
			} else {
				result.put("code", -1);
			}
			this.send(response, result);
		}
	}
}










