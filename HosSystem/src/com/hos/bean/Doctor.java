package com.hos.bean;

public class Doctor {
	private int did;
	private int kid;
	private String dname;
	private String msg;
	private String pic;
	
	@Override
	public String toString() {
		return "Doctor [did=" + did + ", kid=" + kid + ", dname=" + dname + ", msg=" + msg + ", pic=" + pic + "]";
	}

	public Doctor(int did, int kid, String dname, String msg, String pic) {
		super();
		this.did = did;
		this.kid = kid;
		this.dname = dname;
		this.msg = msg;
		this.pic = pic;
	}

	public Doctor() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + did;
		result = prime * result + kid;
		result = prime * result + ((dname == null) ? 0 : dname.hashCode());
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + ((pic == null) ? 0 : pic.hashCode());
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
		Doctor other = (Doctor) obj;
		if (did != other.did)
			return false;
		if (kid != other.kid)
			return false;
		if (dname == null) {
			if (other.dname != null)
				return false;
		} else if (!dname.equals(other.dname))
			return false;
		if (msg == null) {
			if (other.msg != null)
				return false;
		} else if (!msg.equals(other.msg))
			return false;
		if (pic == null) {
			if (other.pic != null)
				return false;
		} else if (!pic.equals(other.pic))
			return false;
		return true;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getKid() {
		return kid;
	}

	public void setKid(int kid) {
		this.kid = kid;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
}
