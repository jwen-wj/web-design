package com.hos.bean;

import java.util.Date;

public class OrderBackShow {
	private int oid;
	private String uname;
	private String dname;
	private Date sdate;
	
	@Override
	public String toString() {
		return "OrderBackShow [oid=" + oid + ", uname=" + uname + ", dname=" + dname + ", sdate=" + sdate + "]";
	}

	public OrderBackShow(int oid, String uname, String dname, Date sdate) {
		super();
		this.oid = oid;
		this.uname = uname;
		this.dname = dname;
		this.sdate = sdate;
	}

	public OrderBackShow() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dname == null) ? 0 : dname.hashCode());
		result = prime * result + oid;
		result = prime * result + ((sdate == null) ? 0 : sdate.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
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
		OrderBackShow other = (OrderBackShow) obj;
		if (dname == null) {
			if (other.dname != null)
				return false;
		} else if (!dname.equals(other.dname))
			return false;
		if (oid != other.oid)
			return false;
		if (sdate == null) {
			if (other.sdate != null)
				return false;
		} else if (!sdate.equals(other.sdate))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		return true;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	
}
