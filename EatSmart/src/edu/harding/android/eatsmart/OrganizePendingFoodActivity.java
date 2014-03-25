package edu.harding.android.eatsmart;

import android.support.v4.app.Fragment;

public class OrganizePendingFoodActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		
		return new OrganizePendingFoodFragment();
	}

}

