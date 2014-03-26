package edu.harding.android.eatsmart;


public class Day {

	private long mId;
	private String mDate;
	private int mTotalCalories;
	
	public Day(){
		mId = -1;
		mDate = null;
		mTotalCalories = 0;
	}
	
	public Day(String date){
		mId = -1;
		mDate = date;
	}
	public long getId() {
		return mId;
	}
	public void setId(long id) {
		mId = id;
	}
	public String getDate() {
		return mDate;
	}
	public void setDate(String date) {
		this.mDate = date;
	}

	public int getTotalCalories() {
		return mTotalCalories;
	}

	public void setTotalCalories(int totalCalories) {
		mTotalCalories = totalCalories;
	}
	
	
}
