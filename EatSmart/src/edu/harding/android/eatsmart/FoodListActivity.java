package edu.harding.android.eatsmart;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;

@SuppressLint("Registered")
public class FoodListActivity extends SingleFragmentActivity {
	@Override
    protected Fragment createFragment() {
        return new FoodListFragment();
    }
}
