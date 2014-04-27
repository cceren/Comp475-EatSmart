package edu.harding.android.eatsmart;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StayHealthy extends Activity {
	private String mSuggestedCalIntake;
	private SharedPreferences mSharedPreferences;
	
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
	    
	    mSharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		mSuggestedCalIntake = mSharedPreferences.getString("suggestedCalories", "");
		if(mSuggestedCalIntake == null){
			mSuggestedCalIntake = "2000";
		}
		TextView suggestedCaloriesTextView = (TextView)findViewById(R.id.calorieAmountTextView);
        suggestedCaloriesTextView.setText(mSuggestedCalIntake);
        
        Button setCalorieGoal = (Button)findViewById(R.id.setCalorieGoal);
		setCalorieGoal.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				Editor editor = mSharedPreferences.edit();
				editor.putString("calorieIntakeGoal", mSuggestedCalIntake);  
		        editor.commit();
		        
				int duration = Toast.LENGTH_SHORT;
		        final Toast toast = Toast.makeText(getBaseContext(), "Calorie Goal was set to: " + mSuggestedCalIntake, duration);
		        toast.show();				            
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		NavUtils.navigateUpFromSameTask(this);
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
