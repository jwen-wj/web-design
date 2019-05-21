package com.hos.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.google.gson.Gson;
import com.hos.util.SmartUploadUtil;

@WebServlet("/UploadServlet")
public class UploadServlet extends BasicServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SmartUploadUtil suu = new SmartUploadUtil();
		PageContext pageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = suu.upload(pageContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = new Gson().toJson(map);
		result = result.replaceAll("\"\\[\\]\"", "[]");
		this.send(response, result);
	}
}
