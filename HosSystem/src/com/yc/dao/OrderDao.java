package com.yc.dao;

import java.util.List;

import com.hos.bean.Order;
import com.hos.bean.OrderBackShow;
import com.hos.bean.OrderShow;

public class OrderDao {
	public int getCount(String sid) {
		DBHelper db = new DBHelper();
		int result = db.findInt("select count(*) from hos_order where sid=?", sid);
		return result;
	}
	
	public int addOrder(String usid, String sid) {
		DBHelper db = new DBHelper();
		int result = db.update("insert into hos_order (usid, sid) values(?, ?)", usid, sid);
		return result;
	}
	
	public List<Order> getAllOrders(){
		DBHelper db = new DBHelper();
		List<Order> result = db.findObjects(Order.class, "select * from hos_order");
		return result;
	}
	
	public List<OrderShow> getOrderShows(String usid){
		DBHelper db = new DBHelper();
		List<OrderShow> result = db.findObjects(OrderShow.class, "select dname, kname, sdate, timetype, price from hos_order, hos_schedu, hos_doctor, hos_ks where "
				+ "hos_order.sid = hos_schedu.sid and hos_schedu.did = hos_doctor.did and hos_doctor.kid = hos_ks.kid and hos_order.usid = ? order by sdate desc, timetype;", usid);
		return result;
	}
	
	public List<OrderBackShow> getOrderBackShows(){
		DBHelper db = new DBHelper();
		List<OrderBackShow> result = db.findObjects(OrderBackShow.class, "select oid, uname, dname, sdate from hos_order, hos_user, hos_doctor, hos_schedu "
				+ "where hos_order.usid = hos_user.usid and hos_order.sid = hos_schedu.sid and hos_schedu.did = hos_doctor.did;");
		return result;
	}
}


















