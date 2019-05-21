package com.hos.util;

import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.jsp.PageContext;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;

public class SmartUploadUtil {
	
	public static String filePath = "images";
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public Map<String, String> upload(PageContext pageContext) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SmartUpload su = new SmartUpload();
		su.initialize(pageContext);
		
		su.setAllowedFilesList("jpg,jpeg,png,gif");
		su.setCharset("utf-8");
		su.setMaxFileSize(1024 * 1024 * 10);
		
		su.upload();
		
		Request request = su.getRequest();
		Enumeration<String> en = request.getParameterNames();
		String key;
		while(en.hasMoreElements()) {
			key = en.nextElement();
			map.put(key, request.getParameter(key));
		}
		
		Files files = su.getFiles();
		String sep = java.io.File.separator;
		Collection<File> fls = files.getCollection();
		String dirPath = pageContext.getRequest().getRealPath("/") + sep + ".." + sep + filePath + sep;
		String filename = "";
		String fieldname = "";
		Map<String, String> fieldMap = new HashMap<String, String>();
		Random rd = new Random();
		for(File file : fls) {
			if(!file.isMissing()) {
				filename = new Date().getTime() + rd.nextInt(1000) + "_" + file.getFileName();
				file.saveAs(dirPath + filename);
				fieldname = file.getFieldName();
				if(fieldMap.containsKey(fieldname)) {
					fieldMap.put(fieldname, fieldMap.get(fieldname) + "," + filePath + "/" + filename);
				} else {
					fieldMap.put(fieldname, filePath + "/" + filename);
				}
			}
		}
		Set<String> fields = fieldMap.keySet();
		for(String field : fields) {
			map.put(field, fieldMap.get(field));
		}
		
		return map;
	}

}




















