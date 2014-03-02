package edu.harding.android.eatsmart;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile {

	private static final String JSON_NAME = "name";
	//private static final String JSON_AGE = "age";
	//private static final String JSON_WEIGHT = "weight";
	//private static final String JSON_HEIGHT = "height";
	
	private String mName;
	private int mAge;
	private int mWeight;
	private int mHeight;
	
	public Profile(JSONObject json)throws JSONException{
		if(json.has(JSON_NAME)){
			if(mName.equals("")) 
				mName = "Guest";
			else
				mName = json.getString(JSON_NAME);
		}
		else
			mName = "Guest";
		
		//mAge = json.getInt(JSON_AGE);
		//mWeight = json.getInt(JSON_WEIGHT);
		//mHeight = json.getInt(JSON_HEIGHT);
	}
	
	public JSONObject toJSON() throws JSONException{
		JSONObject json = new JSONObject();
		json.put(JSON_NAME, mName);
		//json.put(JSON_AGE, mAge);
		//json.put(JSON_HEIGHT, mHeight);
		//json.put(JSON_WEIGHT, mWeight);
		return json;
	}
	
	public Profile(){
		mName = "Guest";
	}
	
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public int getAge() {
		return mAge;
	}
	public void setAge(int age) {
		mAge = age;
	}
	public int getWeight() {
		return mWeight;
	}
	public void setWeight(int weight) {
		mWeight = weight;
	}
	public int getHeight() {
		return mHeight;
	}
	public void setHeight(int height) {
		mHeight = height;
	}
	
	
}
