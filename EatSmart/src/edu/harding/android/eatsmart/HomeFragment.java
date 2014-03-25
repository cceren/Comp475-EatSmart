package edu.harding.android.eatsmart;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	private static final int REQUEST_PHOTO = 1;
	private final String USERINFO = "userInfo";
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.app_name);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		
		View v = inflater.inflate(R.layout.fragment_home, parent, false);
		//Set the welcomeTextView to be the name of the user (saved in profile)
		//TextView welcomeTextView = (TextView)v.findViewById(R.id.welcome_textView);
		//welcomeTextView.setText(mProfile.getName());
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("name", "");
        String birthday = sharedPreferences.getString("birthday", "");
        String userWeight = sharedPreferences.getString("weight", "");
        String userHeight = sharedPreferences.getString("height", "");
        
        TextView profileNameTextView = (TextView)v.findViewById(R.id.profileName_textView);
        TextView birthdayTextView = (TextView)v.findViewById(R.id.birthday_textView);
        TextView heightTextView = (TextView)v.findViewById(R.id.height_textView);
        TextView weightTextView = (TextView)v.findViewById(R.id.weight_textView);
        
        profileNameTextView.setText("User Name: " + userName);
        birthdayTextView.setText("Birthday:"+ birthday);
        heightTextView.setText("Height: " + userHeight);
        weightTextView.setText("Weight: " + userWeight);
		Button addFoodButton = (Button)v.findViewById(R.id.add_food_button);
		addFoodButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FoodListFragment foodListFragment = new FoodListFragment();
                ft.replace(R.id.fragmentContainer, foodListFragment).addToBackStack("Back")
                .commit();            
			}
		});
		
		//***Launches the ConsumedFoodListFragment***
		Button historyButton = (Button)v.findViewById(R.id.history_button);
		historyButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ConsumedFoodListFragment consumedFoodListFragment = new ConsumedFoodListFragment();
                ft.replace(R.id.fragmentContainer, consumedFoodListFragment).addToBackStack("Back")
                .commit();            
			}
		});
		
		//***Starts the activity that takes the picture***
		Button quickPickButton = (Button)v.findViewById(R.id.quick_pick_button);
		quickPickButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), FoodCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
				
			}
		});
		
		 // if camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
        	quickPickButton.setEnabled(false);
        }
		
        //***Launches PendingFoodListFragment***
		Button organizeButton = (Button)v.findViewById(R.id.organize_button);
		organizeButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), PendingFoodListActivity.class);
                startActivity(i);
				
			}
		});
		
		
		//Launches ProfileFragment
		Button updateProfileButton = (Button)v.findViewById(R.id.update_profile_button);
		updateProfileButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProfileFragment profileFragment = new ProfileFragment();
                ft.replace(R.id.fragmentContainer, profileFragment).addToBackStack("Back")
                .commit(); 
			}
		});
		return v;
	}
	
	 @Override
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode != Activity.RESULT_OK) return;
	        if (requestCode == REQUEST_PHOTO) {
	        	
	        	String filename = data
	        		.getStringExtra(FoodCameraFragment.EXTRA_PHOTO_FILENAME);
	        	
	        	
	        	if(filename != null){
	        		//Create a food Item and add the photo to it 
	        		Food pendingFood = new Food();
		        	pendingFood.setTitle("Pending");
		        	pendingFood.setCalories(0);
		        	pendingFood.setQuantity(1);
		        	pendingFood.setPhotoDate(CurrentDate());
		        	pendingFood.setPhotoFilename(filename);
		        	
		        	//Add the food to the PendingFood table
		        	FoodManager.get(getActivity()).addPendingFood(pendingFood);
		        } 
	        }
	   }
	 
	 /***
	  * Returns a string with today's current date in "yyyy-MM-dd" format
	  * @return currentDate
	  */
	 String CurrentDate(){
	 
		 Date date = new Date();
	     String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
	     return currentDate;
	 }
}
