package edu.harding.android.eatsmart;

import java.util.Date;

public class Day {

	private long mId;
	private Date date;
	public long getId() {
		return mId;
	}
	public void setId(long id) {
		mId = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
