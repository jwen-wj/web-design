package com.yc.dao;

import java.util.List;

import com.hos.bean.Schedu;

public class ScheduDao {
	public Schedu getSchedu(String did, String sdate, String timetype){
		DBHelper db = new DBHelper();
		Schedu result = db.findObject(Schedu.class, "select * from hos_schedu where did=? "
				+ "and sdate = date_format(?, '%Y%m%d') and timetype like ?", did, sdate, "%"+timetype+"%");
		return result;
	}
	
	public List<Schedu> getSchedus(String did, String sdate){
		DBHelper db = new DBHelper();
		List<Schedu> result = db.findObjects(Schedu.class, "select * from hos_schedu where did=? "
				+ "and sdate = date_format(?, '%Y%m%d')", did, sdate);
		return result;
	}
	
	public Schedu getScheduBySid(String sid) {
		DBHelper db = new DBHelper();
		Schedu result = db.findObject(Schedu.class, "select * from hos_schedu where sid=?", sid);
		return result;
	}
	
	public List<Schedu> getAllSchedus(){
		DBHelper db = new DBHelper();
		List<Schedu> result = db.findObjects(Schedu.class, "select * from hos_schedu");
		return result;
	}
}
