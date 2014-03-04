package edu.harding.android.eatsmart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class HomeActivity extends SingleFragmentActivity {
	private final String USERINFO = "userInfo"; 
	@Override
	protected Fragment createFragment() {
		
		// Launch either the settingsFragment or the HomeFragment 
		try{
			SharedPreferences sharedPreferences = this.getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
	        String userName = sharedPreferences.getString("name", "");
	        if(userName.equals("")){
				return new ProfileFragment();
	        }
		
		}catch(Exception e){
			Log.e("ERROR", "Error loading the profile");
		}
		
		return new HomeFragment();
	}
	
	
}
