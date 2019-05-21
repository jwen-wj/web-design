package com.hos.bean;

public class DoctorShow {
	private String dname;
	private String kname;
	private String msg;
	private String pic;
	
	@Override
	public String toString() {
		return "DoctorShow [dname=" + dname + ", kname=" + kname + ", msg=" + msg + ", pic=" + pic + "]";
	}

	public DoctorShow(String dname, String kname, String msg, String pic) {
		super();
		this.dname = dname;
		this.kname = kname;
		this.msg = msg;
		this.pic = pic;
	}

	public DoctorShow() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dname == null) ? 0 : dname.hashCode());
		result = prime * result + ((kname == null) ? 0 : kname.hashCode());
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
		DoctorShow other = (DoctorShow) obj;
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
