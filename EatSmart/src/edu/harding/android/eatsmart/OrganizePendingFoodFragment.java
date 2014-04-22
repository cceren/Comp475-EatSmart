package edu.harding.android.eatsmart;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
	public static OrganizePendingFoodFragment newInstance(String imagePath){
		Bundle args = new Bundle();
		
		args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
		
		OrganizePendingFoodFragment fragment = new OrganizePendingFoodFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.pending_food);
	}
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_organize_pending_food, parent, false);
		
		mImageView = (ImageView)v.findViewById(R.id.pendingFoodImageView);
		
		mPath = (String)getArguments().getSerializable(EXTRA_IMAGE_PATH);
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
				String servings = mCaloriesEditText.getText().toString();
				String name = mCaloriesEditText.getText().toString();
				if(cals.equals(""))
					mCalories = 0;
				mCalories = Integer.parseInt(cals);
				if(servings.equals(""))
					mServings = 0;
				mServings = Integer.parseInt(servings);
				if(name.equals(""))
					mFoodName = "NULL";
				mFoodName = mFoodNameEditText.getText().toString();
//				Log.e("name cals servings", mFoodName+mCalories+mServings);
				saveFoodToDatabase();
				getActivity().setResult(Activity.RESULT_OK);
				getActivity().finish();
			}
		});
		return v;
	}

	void saveFoodToDatabase(){
		Food food = new Food();
		food.setTitle(mFoodName);
		food.setCalories(mCalories);
		food.setQuantity(mServings);

		FoodManager.get(getActivity()).addOrganizedPendingFood(food);
		FoodManager.get(getActivity()).DeletePendingFood(mPath);
	}
	@Override
	public void onDestroyView(){
		super.onDestroy();
		PictureUtils.cleanImageView(mImageView);
	}
	
}
