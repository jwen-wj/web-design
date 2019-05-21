package com.hos.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hos.bean.Blacklist;
import com.hos.util.CodeUtil;
import com.hos.util.KeyUtil;
import com.hos.util.MailUtil;
import com.yc.dao.BlacklistDao;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends BasicServlet {

	private static final long serialVersionUID = -8632808230045385155L;
	
	String basePath = "/usr/local/Tomcat/apache-tomcat-9.0.11/webapps/";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("op");
		
		if("compare".equals(op)){
			String cameraPic = basePath + request.getParameter("cameraPic");
			String cardPic = basePath + request.getParameter("cardPic");
			String result = doCompare(cameraPic, cardPic);
			request.getSession().setAttribute(KeyUtil.REGISTER_CMP, result);
			this.send(response, result);
		} 
		else if("email".equals(op)){
			MailUtil mu = new MailUtil();
			String code = CodeUtil.getCode();
			request.getSession().setAttribute(KeyUtil.REGISTER_CODE, code);
			String address = request.getParameter("address");
			mu.send(address, "来自预约挂号平台的邮件！", "尊敬的用户您好,您本次使用的验证码是:" + code + "\r\n" + 
					"\r\n" + 
					"-------------------------------------\r\n" + 
					"很感谢您使用本系统，请尽快使用验证码，验证码将在半小时内失效！\r\n" + 
					"不要告诉他人验证码哦！！！\r\n" + 
					"-------------------------------------\r\n" + 
					"来自 预约挂号平台");
			this.send(response, 1);
		}
		else if("validate".equals(op)) {
/*			String code = request.getParameter("code");
			String trueCode = null;
			Object temp = request.getSession().getAttribute(KeyUtil.REGISTER_CODE);
			if(temp != null) {
				trueCode = String.valueOf(temp);
			}
			if(code == null || !code.equals(trueCode)) {
				this.send(response, 2);			// 未通过邮箱验证
			} else {
				String status = null;
				temp = request.getSession().getAttribute(KeyUtil.REGISTER_CMP);
				if(temp != null) {
					status = String.valueOf(temp);
				}
				if(!"1".equals(status)) {
					this.send(response, 3);		// 未通过身份验证
				} else {
					this.send(response, 1);		// 通过验证
				}
			}*/
			String status = null;
			Object temp = request.getSession().getAttribute(KeyUtil.REGISTER_CMP);
			if(temp != null) {
				status = String.valueOf(temp);
			}
			if(!"1".equals(status)) {
				this.send(response, 3);		// 未通过身份验证
			} else {
				this.send(response, 1);		// 通过验证
			}
		}
	}
	
	String pyFile = "/root/web/face/model1_dlib.py";
	String pyFile2 = "/root/web/face/rlsb.py";
	String predictorPath = "/root/web/face/shape_predictor_68_face_landmarks.dat";
	String modelPath = "/root/web/face/dlib_face_recognition_resnet_model_v1.dat";
	
/*	private String doCompare(String cameraPic, String cardPic) {
		String line = null;
		try {
		    String[] args = new String[] { "python", pyFile, predictorPath, modelPath, cameraPic, cardPic };
		    Process proc = Runtime.getRuntime().exec(args);		// 执行py文件
		 
		    BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		    line = in.readLine();
		    in.close();
		} catch (IOException e) {
		    e.printStackTrace();
		} 
		if("1".equals(line)) {
			return "1";
		} else {
			return "0";
		}
	}*/
	
	private String doCompare(String cameraPic, String cardPic) {
		boolean line = false;
		String result1 = null, result2 = "1";
		try {
		    String[] args = new String[] { "python", pyFile, predictorPath, modelPath, cameraPic, cardPic };
		    Process proc = Runtime.getRuntime().exec(args);// 执行py文件
		    BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		    result1 = in.readLine();		// 为1表示身份匹配
		    in.close();
		    
		    BlacklistDao bldao = new BlacklistDao();
		    List<Blacklist> blist = bldao.getBlacklists();
		    for(Blacklist bl : blist) {
		    	String imgPath = basePath + bl.getPhoto();
			    String[] args2 = new String[] { "python", pyFile2, cameraPic, imgPath };
			    Process proc2 = Runtime.getRuntime().exec(args2);
			    BufferedReader in2 = new BufferedReader(new InputStreamReader(proc2.getInputStream()));
			    String temp = in2.readLine();	
			    if("1".equals(temp)) {
			    	result2 = "0";
			    	break;
			    }
			    in2.close();
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		line = ("1".equals(result1) && "1".equals(result2));
		if(line) {
			return "1";
		} else {
			return "0";
		}
	}
	
}
