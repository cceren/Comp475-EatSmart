package edu.harding.android.eatsmart;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class OrganizePendingFoodFragment extends Fragment{
	
	public static final String EXTRA_IMAGE_PATH =
	"edu.harding.android.eatsmart.image_path";
	public static final String EXTRA_ID =
			"edu.harding.android.eatsmart.id";
	public static final String EXTRA_DATE =
	"edu.harding.android.eatsmart.date";
	public static final String EXTRA_TIME =
	"edu.harding.android.eatsmart.time";
	public static final String TAG =
			"OrganizePendingFoodFragment";
	private ImageView mImageView;
	private int mCalories;
	private int mServings;
	private String mFoodName;
	private EditText mCaloriesEditText;
	private EditText mServingsEditText;
	private EditText mFoodNameEditText;
	private String mPath;
	private long mId;
	private String mDate;
	private String mTime;
	
	public static OrganizePendingFoodFragment newInstance(String imagePath, String date, String time){
		Bundle args = new Bundle();
		
		args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
		args.putSerializable(EXTRA_DATE, date);
		args.putSerializable(EXTRA_TIME, time);
		
		OrganizePendingFoodFragment fragment = new OrganizePendingFoodFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
		getActivity().setTitle(R.string.pending_food);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity()) != null){
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_organize_pending_food, parent, false);
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		}
		mImageView = (ImageView)v.findViewById(R.id.pendingFoodImageView);
		
		mPath = (String)getArguments().getSerializable(EXTRA_IMAGE_PATH);
		mDate = (String)getArguments().getSerializable(EXTRA_DATE);
		mTime = (String)getArguments().getSerializable(EXTRA_TIME);
		String path = getActivity()
	    		   .getFileStreamPath(mPath).getAbsolutePath();
		
		BitmapDrawable image = PictureUtils.getScaledDrawable(getActivity(), path);
		
		mImageView.setImageDrawable(image);
		
		 mCaloriesEditText = (EditText)v.findViewById(R.id.caloriesEditText);
		 mServingsEditText = (EditText)v.findViewById(R.id.servingsEditText);
		 mFoodNameEditText = (EditText)v.findViewById(R.id.foodNameEditText);
		 
		 Button saveButton = (Button)v.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String cals = mCaloriesEditText.getText().toString();
            	String servings = mServingsEditText.getText().toString();
            	String name = mFoodNameEditText.getText().toString();
            	
            	if(cals.toString().length() >= 1){
            		mCalories = Integer.parseInt(cals);
            	}
            	if(servings.toString().length() >= 1){
            		mServings = Integer.parseInt(servings);
            	}else{
            		mServings = 1;
            	}
				if(name.toString().length() >= 1){
					mFoodName = mFoodNameEditText.getText().toString();
				}
				if(mCalories > 0 && mFoodName != null && mServings > 0){
				Log.d(TAG, mFoodName+mCalories+mServings);
				saveFoodToDatabase();
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
				}
				else{
					showAlert();
				}
			}
		});
		return v;
	}

	void saveFoodToDatabase(){
		Food food = new Food();
		food.setDay(mDate);
		food.setTime(mTime);
		food.setTitle(mFoodName);
		food.setCalories(mCalories);
		food.setQuantity(mServings);
		
		Log.d(TAG, food.getDay());
		FoodManager.get(getActivity()).addConsumedFoodToDatabase(food);
		FoodManager.get(getActivity()).DeletePendingFood(mPath);
	}
	@Override
	public void onDestroyView(){
		super.onDestroy();
		PictureUtils.cleanImageView(mImageView);
	}
	
	private void showAlert(){
		new AlertDialog.Builder(getActivity())
	    .setTitle("Incomplete Form")
	    .setMessage("All fields are required.")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    
	     
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
	
	
}
