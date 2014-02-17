package edu.harding.android.eatsmart;

import java.util.Date;
import java.util.UUID;

public class Food {
	
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private int mQuantity;
    private int mCalories;

    public Food() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public String getTitle() {
        return mTitle;
    }
    
    public void setTitle(String title){
    	mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity= quantity;
    }
    
    public int getCalories(){
    	return mCalories;
    }

    public Date getDate() {
        return mDate;
    }

	public void setDate(Date date) {
	        mDate = date;
	}
	
}
