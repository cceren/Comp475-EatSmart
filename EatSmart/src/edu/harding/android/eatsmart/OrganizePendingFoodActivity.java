package edu.harding.android.eatsmart;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class OrganizePendingFoodActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		Bundle b = getIntent().getExtras();
		String path = b.getString("filename");
		String date = b.getString("date");
		String time = b.getString("time");
		return  OrganizePendingFoodFragment.newInstance(path, date, time);
	}

}

