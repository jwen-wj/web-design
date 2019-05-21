package com.hos.bean;

public class Blacklist {
	private int bid;
	private String photo;
	private String btype;
	
	@Override
	public String toString() {
		return "blacklist [bid=" + bid + ", photo=" + photo + ", btype=" + btype + "]";
	}

	public Blacklist(int bid, String photo, String btype) {
		super();
		this.bid = bid;
		this.photo = photo;
		this.btype = btype;
	}

	public Blacklist() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bid;
		result = prime * result + ((btype == null) ? 0 : btype.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
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
		Blacklist other = (Blacklist) obj;
		if (bid != other.bid)
			return false;
		if (btype == null) {
			if (other.btype != null)
				return false;
		} else if (!btype.equals(other.btype))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		return true;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}
	
}
