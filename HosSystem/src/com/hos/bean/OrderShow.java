package com.hos.bean;

import java.util.Date;

public class OrderShow {
	private String dname;
	private String kname;
	private Date sdate;
	private String timetype;
	private double price;
	
	@Override
	public String toString() {
		return "ShowOrder [dname=" + dname + ", kname=" + kname + ", sdate=" + sdate + ", timetype=" + timetype
				+ ", price=" + price + "]";
	}

	public OrderShow(String dname, String kname, Date sdate, String timetype, double price) {
		super();
		this.dname = dname;
		this.kname = kname;
		this.sdate = sdate;
		this.timetype = timetype;
		this.price = price;
	}

	public OrderShow() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dname == null) ? 0 : dname.hashCode());
		result = prime * result + ((kname == null) ? 0 : kname.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((sdate == null) ? 0 : sdate.hashCode());
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
		OrderShow other = (OrderShow) obj;
		if (dname == null) {
			if (other.dname != null)
				return false;
		} else if (!dname.equals(other.dname))
			return false;
		if (kname == null) {
			if (other.kname != null)
				return false;
		} else if (!kname.equals(other.kname))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (sdate == null) {
			if (other.sdate != null)
				return false;
		} else if (!sdate.equals(other.sdate))
			return false;
		if (timetype == null) {
			if (other.timetype != null)
				return false;
		} else if (!timetype.equals(other.timetype))
			return false;
		return true;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getKname() {
		return kname;
	}

	public void setKname(String kname) {
		this.kname = kname;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
