package edu.harding.android.eatsmart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class HomeActivity extends SingleFragmentActivity {
	private final String USERINFO = "userInfo"; 
	
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        
		   super.onCreate(savedInstanceState);
	    }
	
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
