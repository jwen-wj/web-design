package com.hos.bean;

public class Jb {
	private int jid;
	private int kid;
	private String jname;
	private String stype;
	private String btype;
	private int hot;
	
	@Override
	public String toString() {
		return "Jb [jid=" + jid + ", kid=" + kid + ", jname=" + jname + ", stype=" + stype + ", btype=" + btype
				+ ", hot=" + hot + "]";
	}

	public Jb(int jid, int kid, String jname, String stype, String btype, int hot) {
		super();
		this.jid = jid;
		this.kid = kid;
		this.jname = jname;
		this.stype = stype;
		this.btype = btype;
		this.hot = hot;
	}

	public Jb() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((btype == null) ? 0 : btype.hashCode());
		result = prime * result + hot;
		result = prime * result + jid;
		result = prime * result + ((jname == null) ? 0 : jname.hashCode());
		result = prime * result + kid;
		result = prime * result + ((stype == null) ? 0 : stype.hashCode());
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
		Jb other = (Jb) obj;
		if (btype == null) {
			if (other.btype != null)
				return false;
		} else if (!btype.equals(other.btype))
			return false;
		if (hot != other.hot)
			return false;
		if (jid != other.jid)
			return false;
		if (jname == null) {
			if (other.jname != null)
				return false;
		} else if (!jname.equals(other.jname))
			return false;
		if (kid != other.kid)
			return false;
		if (stype == null) {
			if (other.stype != null)
				return false;
		} else if (!stype.equals(other.stype))
			return false;
		return true;
	}

	public int getJid() {
		return jid;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}

	public int getKid() {
		return kid;
	}

	public void setKid(int kid) {
		this.kid = kid;
	}

	public String getJname() {
		return jname;
	}

	public void setJname(String jname) {
		this.jname = jname;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}
	
}
