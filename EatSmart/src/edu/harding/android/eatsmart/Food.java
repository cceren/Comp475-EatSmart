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
	private static final String JSON_PHOTO = "photo";
	
    private UUID mId;
    private int mfoodId;
    private String mTitle;
    private Date mDate;
    private String mPhotoDate;
    private String mPhotoFilename;
	private int mQuantity;
    private int mCalories;
    private Photo mPhoto;
    
    public String getPhotoFilename() {
		return mPhotoFilename;
	}

	public void setPhotoFilename(String photoFilename) {
		mPhotoFilename = photoFilename;
	}
	
     public String getPhotoDate() {
		return mPhotoDate;
	}

	public void setPhotoDate(String photoDate) {
		mPhotoDate = photoDate;
	}
    public Food() {
        //mId = UUID.randomUUID();
        mDate = new Date();
    }
    
    public Food(JSONObject json) throws JSONException {
    	mId = UUID.fromString(json.getString(JSON_ID));
    	mTitle = json.getString(JSON_TITLE);
    	mDate = new Date(json.getLong(JSON_DATE));
    	mQuantity = json.getInt(JSON_QUANTITY);
    	mCalories = json.getInt(JSON_CALORIES);
    	if (json.has(JSON_PHOTO))
    		mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
    }
    
    public JSONObject toJSON() throws JSONException {
    	JSONObject json = new JSONObject();
    	json.put(JSON_ID, mId.toString());
    	json.put(JSON_TITLE, mTitle);
    	json.put(JSON_DATE, mDate.getTime());
    	json.put(JSON_QUANTITY, mQuantity);
    	json.put(JSON_CALORIES, mCalories);
    	if(mPhoto != null)
    		json.put(JSON_PHOTO, mPhoto.toJSON());
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
	
	public Photo getPhoto(){
		return mPhoto;
	}
	
	public void setPhoto(Photo p){
		mPhoto = p;
	}

	public int getFoodId() {
		return mfoodId;
	}

	public void setFoodId(int foodId) {
		mfoodId = foodId;
	}
	
	
}
