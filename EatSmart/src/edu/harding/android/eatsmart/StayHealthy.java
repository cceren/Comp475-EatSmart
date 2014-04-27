package edu.harding.android.eatsmart;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class StayHealthy extends Activity {
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.suggestions_page);
	    getActionBar().setDisplayHomeAsUpEnabled(true);
	    ActionBar actionBar = getActionBar();
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	    actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	    
		SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		String suggestedCalorieIntake = sharedPreferences.getString("suggestedCalories", "");
		if(suggestedCalorieIntake == null){
			suggestedCalorieIntake = "2000";
		}
		TextView suggestedCaloriesTextView = (TextView)findViewById(R.id.calorieAmountTextView);
        suggestedCaloriesTextView.setText(suggestedCalorieIntake);
        

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	
}
