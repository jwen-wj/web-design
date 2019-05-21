package com.hos.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class MailUtil {
	public void send(String address, String subject, String text) {
		Properties props = new Properties();
		props.setProperty("mail.debug", "true");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "smtp.163.com");
		
		Session session = Session.getInstance(props);
		
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress("y739465364@163.com"));
			message.setSubject(subject);
			message.setText(text);
			
			Transport transport = session.getTransport();
			transport.connect("y739465364@163.com", "yanghaofan");
			transport.sendMessage(message, new Address[] {new InternetAddress(address)});
			transport.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
