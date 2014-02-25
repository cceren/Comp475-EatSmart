package edu.harding.android.eatsmart;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Food {
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	private static final String JSON_QUANTITY = "quantity";
	private static final String JSON_CALORIES = "calories";
	
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private int mQuantity;
    private int mCalories;

    public Food() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }
    
    public Food(JSONObject json) throws JSONException {
    	mId = UUID.fromString(json.getString(JSON_ID));
    	mTitle = json.getString(JSON_TITLE);
    	mDate = new Date(json.getLong(JSON_DATE));
    	mQuantity = json.getInt(JSON_QUANTITY);
    	mCalories = json.getInt(JSON_CALORIES);
    }
    
    public JSONObject toJSON() throws JSONException {
    	JSONObject json = new JSONObject();
    	json.put(JSON_ID, mId.toString());
    	json.put(JSON_TITLE, mTitle);
    	json.put(JSON_DATE, mDate.getTime());
    	json.put(JSON_QUANTITY, mQuantity);
    	json.put(JSON_CALORIES, mCalories);
    	return json;
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

    public void incrementQuantity() {
        mQuantity++;
    }
    
    public void setQuantity(int quantity){
    	mQuantity = quantity;
    }
    
    public int getCalories(){
    	return mCalories;
    }
    public void setCalories(int cals){
    	mCalories = cals;
    }

    public Date getDate() {
        return mDate;
    }

	public void setDate(Date date) {
	        mDate = date;
	}
	
}
