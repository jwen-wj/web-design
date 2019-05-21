package com.hos.bean;

import java.util.Date;

public class Schedu {
	private int sid;
	private int did;
	private Date sdate;
	private String timetype;
	private int rescount;
	private double price;
	
	@Override
	public String toString() {
		return "Schedu [sid=" + sid + ", did=" + did + ", sdate=" + sdate + ", timetype=" + timetype + ", rescount="
				+ rescount + ", price=" + price + "]";
	}

	public Schedu(int sid, int did, Date sdate, String timetype, int rescount, double price) {
		super();
		this.sid = sid;
		this.did = did;
		this.sdate = sdate;
		this.timetype = timetype;
		this.rescount = rescount;
		this.price = price;
	}

	public Schedu() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + did;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + rescount;
		result = prime * result + ((sdate == null) ? 0 : sdate.hashCode());
		result = prime * result + sid;
		result = prime * result + ((timetype == null) ? 0 : timetype.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedu other = (Schedu) obj;
		if (did != other.did)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (rescount != other.rescount)
			return false;
		if (sdate == null) {
			if (other.sdate != null)
				return false;
		} else if (!sdate.equals(other.sdate))
			return false;
		if (sid != other.sid)
			return false;
		if (timetype == null) {
			if (other.timetype != null)
				return false;
		} else if (!timetype.equals(other.timetype))
			return false;
		return true;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	public int getRescount() {
		return rescount;
	}

	public void setRescount(int rescount) {
		this.rescount = rescount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
