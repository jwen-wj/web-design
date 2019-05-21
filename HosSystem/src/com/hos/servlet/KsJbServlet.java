package com.hos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hos.bean.Ks;
import com.hos.util.StringUtil;
import com.yc.dao.KsJbDao;

@WebServlet("/KsJbServlet")
public class KsJbServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if("getKs".equals(op)) {
			KsJbDao dao = new KsJbDao();
			this.send(response, dao.getKss());
		}
		else if("getJb".equals(op)) {
			KsJbDao dao = new KsJbDao();
			this.send(response, dao.getJbs());
		}
		else if("getJbByKid".equals(op)) {
			String kid = request.getParameter("kid");
			if(!StringUtil.isNull(kid)) {
				KsJbDao dao = new KsJbDao();
				if(kid.contains(",") && !kid.contains(" or ")) {
					this.send(response, dao.getJbsByKids(kid));
				} else {
					this.send(response, dao.getJbsByKid(kid));
				}
			}
		}
		else if("getKsByKid".equals(op)) {
			String kid = request.getParameter("kid");
			if(!StringUtil.isNull(kid)) {
				KsJbDao dao = new KsJbDao();
				this.send(response, dao.getKsByKid(kid));
			}
		}
		else if("SearchKsByKname".equals(op)) {
			String kname = request.getParameter("keyword");
			if(!StringUtil.isNull(kname)) {
				KsJbDao dao = new KsJbDao();
				Ks ks = dao.SearchKsByKname(kname);
				if(ks != null) {
					this.send(response, ks.getKid());
				} else {
					this.send(response, -1);
				}
			}
		}
		else if("SearchKsByJname".equals(op)) {
			String jname = request.getParameter("keyword");
			if(!StringUtil.isNull(jname)) {
				KsJbDao dao = new KsJbDao();
				Ks ks = dao.SearchKsByJname(jname);
				if(ks != null) {
					this.send(response, ks.getKid());
				} else {
					this.send(response, -1);
				}
			}
		}
		else if("getHotKss".equals(op)) {
			KsJbDao dao = new KsJbDao();
			this.send(response, dao.getHotKss());
		}
		else if("getHotJbs".equals(op)) {
			KsJbDao dao = new KsJbDao();
			this.send(response, dao.getHotJbs());
		}
	}
}










