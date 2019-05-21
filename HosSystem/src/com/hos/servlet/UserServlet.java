package com.hos.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hos.util.CodeUtil;
import com.hos.util.KeyUtil;
import com.hos.util.StringUtil;
import com.yc.dao.UserDao;

@WebServlet("/UserServlet")
public class UserServlet extends BasicServlet {
	private static final long serialVersionUID = 8581641169210542597L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("op");
		
		if("login".equals(op)) {
			String email = request.getParameter("email");
			String pwd = request.getParameter("pwd");
			UserDao ud = new UserDao();
			int usid = ud.login(email, CodeUtil.getMD5String(pwd));
			if(usid > 0) {
				request.getSession().setAttribute(KeyUtil.USERID, usid);
				this.send(response, 1);
			}
			else {
				this.send(response, 0);
			}
		}
		else if("logout".equals(op)) {
			request.getSession().setAttribute(KeyUtil.USERID, null);
			this.send(response, 1);
		}
		else if("login_state".equals(op)) {
			Object usid = request.getSession().getAttribute(KeyUtil.USERID);
			if(usid != null) {
				this.send(response, 1);
			} else {
				this.send(response, 0);
			}
		}
		else if("register".equals(op)) {
			String uname = request.getParameter("name");
			String sex = request.getParameter("sex");
			String cardtype = request.getParameter("cardtype");
			String cardid = request.getParameter("cardid");
			String pwd = request.getParameter("pwd");
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String area = request.getParameter("area");
			String address = province + "/" + city + "/" + area;
			String tel = request.getParameter("tel");
			String email = request.getParameter("email");
			String photo = request.getParameter("photo");
			UserDao dao = new UserDao();
			this.send(response, dao.register(uname, sex, cardtype, cardid, CodeUtil.getMD5String(pwd), 
					address, tel, email, photo));
		}
		else if("checkEmail".equals(op)) {
			String email = request.getParameter("email");
			UserDao dao = new UserDao();
			int result = dao.findEmail(email);
			if(result > 0) {
				this.send(response, 0);
			} else {
				this.send(response, 1);
			}
		}
		else if("getUserByUsid".equals(op)) {
			String usid = request.getParameter("usid");
			if(!StringUtil.isNull(usid)) {
				UserDao dao = new UserDao();
				this.send(response, dao.getUserByUsid(usid));
			}
		}
		else if("getAllUsers".equals(op)) {
			UserDao dao = new UserDao();
			this.send(response, dao.getAllUsers());
		}
		else if("getUsersByDid".equals(op)) {
			Object did = request.getSession().getAttribute(KeyUtil.DOCTORID);
			if(did != null) {
				UserDao dao = new UserDao();
				this.send(response, dao.getUsersByDid(String.valueOf(did)));
			}
		}
	}
}


















