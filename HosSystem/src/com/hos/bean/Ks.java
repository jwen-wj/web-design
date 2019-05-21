package com.hos.bean;

public class Ks {
	private int kid;
	private String kname;
	private String ktype;
	private int hot;
	
	@Override
	public String toString() {
		return "Ks [kid=" + kid + ", kname=" + kname + ", ktype=" + ktype + ", hot=" + hot + "]";
	}

	public Ks(int kid, String kname, String ktype, int hot) {
		super();
		this.kid = kid;
		this.kname = kname;
		this.ktype = ktype;
		this.hot = hot;
	}

	public Ks() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hot;
		result = prime * result + kid;
		result = prime * result + ((kname == null) ? 0 : kname.hashCode());
		result = prime * result + ((ktype == null) ? 0 : ktype.hashCode());
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
		Ks other = (Ks) obj;
		if (hot != other.hot)
			return false;
		if (kid != other.kid)
			return false;
		if (kname == null) {
			if (other.kname != null)
				return false;
		} else if (!kname.equals(other.kname))
			return false;
		if (ktype == null) {
			if (other.ktype != null)
				return false;
		} else if (!ktype.equals(other.ktype))
			return false;
		return true;
	}

	public int getKid() {
		return kid;
	}

	public void setKid(int kid) {
		this.kid = kid;
	}

	public String getKname() {
		return kname;
	}

	public void setKname(String kname) {
		this.kname = kname;
	}

	public String getKtype() {
		return ktype;
	}

	public void setKtype(String ktype) {
		this.ktype = ktype;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}
	
}
