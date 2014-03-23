package edu.harding.android.eatsmart;

import java.sql.Date;


public class Day {

	private long mId;
	private Date mDate;
	
	public Day(){
		mId = -1;
		mDate = null;
	}
	public long getId() {
		return mId;
	}
	public void setId(long id) {
		mId = id;
	}
	public Date getDate() {
		return mDate;
	}
	public void setDate(Date date) {
		this.mDate = date;
	}
}
