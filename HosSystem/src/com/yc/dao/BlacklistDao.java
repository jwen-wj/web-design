package com.yc.dao;

import java.util.List;

import com.hos.bean.Blacklist;

public class BlacklistDao {
	public List<Blacklist> getBlacklists(){
		DBHelper db = new DBHelper();
		return db.findObjects(Blacklist.class, "select * from hos_blacklist");
	}
}
