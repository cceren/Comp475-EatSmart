package edu.harding.android.eatsmart;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class OrganizePendingFoodActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		String path = b.getString("filename");
		return  OrganizePendingFoodFragment.newInstance(path);
	}

}

