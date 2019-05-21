package com.hos.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

public class CodeUtil {
	
	public static String getCode() {
		Random rd = new Random();
		String result = "";
		for(int i = 0; i < 6; i++) {
			result += rd.nextInt(10);
		}
		return result;
	}
	
	public static String getMD5String(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }

}
