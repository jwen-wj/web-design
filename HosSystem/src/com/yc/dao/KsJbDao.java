package com.yc.dao;

import java.util.List;

import com.hos.bean.Jb;
import com.hos.bean.Ks;

public class KsJbDao {

	public List<Ks> getKss(){
		DBHelper db = new DBHelper();
		return db.findObjects(Ks.class, "select * from hos_ks");
	}
	
	public List<Jb> getJbs(){
		DBHelper db = new DBHelper();
		return db.findObjects(Jb.class, "select * from hos_jb");
	}
	
	public List<Jb> getJbsByKids(String kids){
		DBHelper db = new DBHelper();
		return db.findObjects(Jb.class, "select * from hos_jb where kid in (" + kids + ")");
	}
	
	public List<Jb> getJbsByKid(String kid){
		DBHelper db = new DBHelper();
		return db.findObjects(Jb.class, "select * from hos_jb where kid=?", kid);
	}
	
	public Ks getKsByKid(String kid) {
		DBHelper db = new DBHelper();
		return db.findObject(Ks.class, "select kname from hos_ks where kid=?", kid);
	}
	
	public Ks SearchKsByKname(String kname) {
		DBHelper db = new DBHelper();
		return db.findObject(Ks.class, "select * from hos_ks where kname like ?", "%"+kname+"%");
	}
	
	public Ks SearchKsByJname(String jname) {
		DBHelper db = new DBHelper();
		return db.findObject(Ks.class, "select * from hos_ks where kid in (select kid from hos_jb where jname like ?)", "%"+jname+"%");
	}
	
	public List<Ks> getHotKss() {
		DBHelper db = new DBHelper();
		return db.findObjects(Ks.class, "select * from hos_ks where kname in "
				+ "('呼吸内科', '传染科', '心胸外科', '骨科', '乳腺外科', '妇科', '眼科', '儿科', '男科')");
	}
	
	public List<Jb> getHotJbs(){
		DBHelper db = new DBHelper();
		return db.findObjects(Jb.class, "select * from hos_jb where jname in "
				+ "('感冒', '高血压', '胃炎', '便秘', '头痛', '失眠', '糖尿病', '贫血', '骨折')");
	}
	
}
















