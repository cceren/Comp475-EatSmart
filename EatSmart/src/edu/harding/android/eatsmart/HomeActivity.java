package edu.harding.android.eatsmart;

import android.support.v4.app.Fragment;

public class HomeActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// Launch either the settingsFragment or the HomeFragment 
		
		return new ProfileFragment();
	}

}
