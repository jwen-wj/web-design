package com.hos.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.misc.BASE64Decoder;


/**
 * basePath为项目同级目录下保存照片的文件夹名，
 * out输出文件夹+文件名，用于前台img的显示
 *
 */
@WebServlet("/uploadPhoto")
public class CameraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String filePath = "camera";
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sep = java.io.File.separator;
		String basePath = filePath + sep;
	    String filePath = request.getSession().getServletContext().getRealPath("/") + sep + ".." + sep + basePath;
	    String fileName = new Date().getTime() + ".png";
	    String imgStr = request.getParameter("image");
	    if (null != imgStr) {
	        imgStr = imgStr.substring(imgStr.indexOf(",") + 1);		//默认传入的参数带类型等参数：data:image/png;base64,
	    }
	    Boolean flag = GenerateImage(imgStr, filePath, fileName);
	    String result = "";
	    if (flag) {
	        result = basePath + fileName;
	    }
//	    System.out.println(result);
	    
	    PrintWriter out = response.getWriter();
	    out.print(result);
	    out.flush();
	    out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/**
	 * 功能描述：base64字符串转换成图片
	 */
	private boolean GenerateImage(String imgStr, String filePath, String fileName) {
	    try {
	        if (imgStr == null) {
	            return false;
	        }
	        BASE64Decoder decoder = new BASE64Decoder();
	        byte[] b = decoder.decodeBuffer(imgStr);
	        File file = new File(filePath);
	        if (!file.exists()) {
	            file.mkdirs();
	        }
	        OutputStream out = new FileOutputStream(filePath + fileName);
	        out.write(b);
	        out.flush();
	        out.close();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
