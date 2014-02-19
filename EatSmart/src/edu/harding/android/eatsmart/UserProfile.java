package edu.harding.android.eatsmart;


import android.content.Context;

public class UserProfile {
	
	 private Context mAppContext;
	 private static UserProfile sUserProfile;
	 private int age;
	 private String name;
	 
	 private UserProfile(Context appContext) {
	        mAppContext = appContext;
	        age = 20;
	        name = "Juan";
	      
	    }
	 public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	 
	 public static UserProfile get(Context c) {
	        if (sUserProfile == null) {
	        	sUserProfile = new UserProfile(c.getApplicationContext());
	        }
	        return sUserProfile;
	    }
}
