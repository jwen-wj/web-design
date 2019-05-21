package com.yc.dao;

import java.util.List;

import com.hos.bean.User;

public class UserDao {
	public int login(String email, String pwd) {
		DBHelper db = new DBHelper();
		int result = db.findInt("select usid from hos_user where email=? and pwd=?", email, pwd);
		return result;
	}
	
	public int register(String uname, String sex, String cardtype, String cardid, 
			String pwd, String address, String tel, String email, String photo) {
		DBHelper db = new DBHelper();
		int result = db.update("insert into hos_user (uname, sex, cardtype, cardid, pwd, address, tel, "
				+ "email, photo) values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
				uname, sex, cardtype, cardid, pwd, address, tel, email, photo);
		return result;
	}
	
	public int findEmail(String email) {
		DBHelper db = new DBHelper();
		int result = db.findInt("select count(*) from hos_user where email=?", email);
		return result;
	}
	
	public User getUserByUsid(String usid) {
		DBHelper db = new DBHelper();
		User result = db.findObject(User.class, "select * from hos_user where usid=?", usid);
		return result;
	}
	
	public List<User> getAllUsers(){
		DBHelper db = new DBHelper();
		List<User> result = db.findObjects(User.class, "select * from hos_user");
		return result;
	}
	
	public List<User> getUsersByDid(String did){
		DBHelper db = new DBHelper();
		List<User> result = db.findObjects(User.class, "select distinct hos_user.* from hos_user, hos_order, hos_schedu "
				+ "where hos_user.usid = hos_order.usid and hos_order.sid = hos_schedu.sid and hos_schedu.did = ?;", did);
		return result;
	}
}













