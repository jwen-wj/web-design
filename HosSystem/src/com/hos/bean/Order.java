package com.hos.bean;

public class Order {
	private int oid;
	private int usid;
	private int sid;
	
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", usid=" + usid + ", sid=" + sid + "]";
	}

	public Order(int oid, int usid, int sid) {
		super();
		this.oid = oid;
		this.usid = usid;
		this.sid = sid;
	}

	public Order() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + oid;
		result = prime * result + sid;
		result = prime * result + usid;
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
		Order other = (Order) obj;
		if (oid != other.oid)
			return false;
		if (sid != other.sid)
			return false;
		if (usid != other.usid)
			return false;
		return true;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public int getUsid() {
		return usid;
	}

	public void setUsid(int usid) {
		this.usid = usid;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
}
