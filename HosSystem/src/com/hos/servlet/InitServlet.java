package com.hos.servlet;

import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import com.hos.util.SmartUploadUtil;


public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		SmartUploadUtil.filePath = makedirs(config, "uploadPath", "images");
		CameraServlet.filePath = makedirs(config, "photoPath", "camera");
	}
	
	private String makedirs(ServletConfig config, String paramName, String defaultParamValue) {
		String temp = config.getInitParameter(paramName);
		String filePath = defaultParamValue;
		if(temp != null && !"".equals(temp)) {
			filePath = temp;
		}
		String basePath = config.getServletContext().getRealPath("/");
		File dir = new File(basePath + "../" + filePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		return filePath;
	}
}
