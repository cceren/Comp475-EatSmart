package edu.harding.android.eatsmart;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.Fragment;

public class HomeActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// Launch either the settingsFragment or the HomeFragment 
		
		return new ProfileFragment();
	}
	
	
}
