package com.yc.dao;

import java.util.List;
import com.hos.bean.Doctor;
import com.hos.bean.DoctorShow;
import com.hos.util.StringUtil;

public class DoctorDao {
	
	public List<Doctor> getDoctors(String kid, String sdate, String timetype){
		DBHelper db = new DBHelper();
		List<Doctor> result;
		if(StringUtil.isNull(timetype)) {
			timetype = "";
		}
		if(StringUtil.isNull(sdate)) {
			result = db.findObjects(Doctor.class, "select * from hos_doctor where kid=? and did in "
					+ "(select did from hos_schedu where timetype like ?)", kid, "%"+timetype+"%");
		} else {
			result = db.findObjects(Doctor.class, "select * from hos_doctor where kid=? and did in "
					+ "(select did from hos_schedu where sdate = date_format(?, '%Y%m%d') and timetype like ?)", kid, sdate, "%"+timetype+"%");
		}
		return result;
	}
	
	public Doctor getDoctor(String did) {
		DBHelper db = new DBHelper();
		Doctor result = db.findObject(Doctor.class, "select * from hos_doctor where did=?", did);
		return result;
	}
	
	public List<Doctor> getDoctorsByKid(String kid){
		DBHelper db = new DBHelper();
		List<Doctor> result = db.findObjects(Doctor.class, "select * from hos_doctor where kid=?", kid);
		return result;
	}
	
	public List<Doctor> getHotDoctors(){
		DBHelper db = new DBHelper();
		List<Doctor> result = db.findObjects(Doctor.class, "select * from hos_doctor where did in(2, 19, 37, 7)");	// 2, 7, 14, 19, 25, 31, 37
		return result;
	}
	
	public List<Doctor> getAllDoctors(){
		DBHelper db = new DBHelper();
		List<Doctor> result = db.findObjects(Doctor.class, "select * from hos_doctor");
		return result;
	}
	
	public int updateDoctor(String did, String kid, String dname, String msg, String pic) {
		DBHelper db = new DBHelper();
		int result = db.update("update hos_doctor set kid=?, dname=?, msg=?, pic=? where did=?", kid, dname, msg, pic, did);
		return result;
	}
	public int login(String did, String dname) {
		DBHelper db = new DBHelper();
		int result = db.update("select count(*) from hos_doctor where did=? and dname=?", did, dname);
		return result;
	}
	public List<DoctorShow> getDoctorShows(){
		DBHelper db = new DBHelper();
		List<DoctorShow> result = db.findObjects(DoctorShow.class, "select dname, kname, msg, pic from hos_doctor, hos_ks where hos_doctor.kid = hos_ks.kid;");
		return result;
	}
	
	public List<Doctor> getDoctorsByPage(int page, int limit){
		DBHelper db = new DBHelper();
		List<Doctor> result = db.findObjects(Doctor.class, "select * from hos_doctor limit ?, ?", (page-1)*limit, limit);
//		List<Map<String, Object>> result = db.find("select * from hos_doctor limit ?, ?", (page-1)*limit, limit);		// 可以直接返回一个map
		return result;
	}
	public int getTotal() {
		DBHelper db = new DBHelper();
		int result = db.findInt("select count(*) from hos_doctor");
		return result;
	}
}











