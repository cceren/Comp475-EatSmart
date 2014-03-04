package edu.harding.android.eatsmart;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

	private static final String FILENAME = "foods.json";
	private static final int REQUEST_PHOTO = 1;
	private Profile mProfile;
	private Context mAppContext;
	private EatSmartJSONSerializer mSerializer;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.app_name);
		mAppContext = getActivity().getApplicationContext();
		mSerializer = new EatSmartJSONSerializer(mAppContext, FILENAME);
		try{
			mProfile = mSerializer.loadProfile();
			Log.e("Saving", "This was loaded: " + mProfile.getName());
		}catch(Exception e){
			Log.e("Saving", "No profile saved");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		
		View v = inflater.inflate(R.layout.fragment_home, parent, false);
		//Set the welcomeTextView to be the name of the user (saved in profile)
		TextView welcomeTextView = (TextView)v.findViewById(R.id.welcome_textView);
		//welcomeTextView.setText(mProfile.getName());
		
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
		
		//Launches the ConsumedFoodListFragment
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
		
		//Starts the activity that takes the picture
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
		
		Button organizeButton = (Button)v.findViewById(R.id.organize_button);
		organizeButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), PendingFoodListActivity.class);
                startActivity(i);
				
			}
		});
		return v;
	}
	
	 @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        if (resultCode != Activity.RESULT_OK) return;
	        if (requestCode == REQUEST_PHOTO) {
		            
	        	//Create a food Item and add the photo to it 
		        	Food f = new Food();
		        	f.setTitle("Pending");
		        	f.setCalories(0);
		        	f.setQuantity(1);
		        	
		        	String filename = data
		        			.getStringExtra(FoodCameraFragment.EXTRA_PHOTO_FILENAME);
		        	
		        	if(filename != null){
		        		Photo p = new Photo(filename);
		        		f.setPhoto(p);	
		        	}
		        	//Add the food to the PendingFoodLab
		        	PendingFoodLab.get(getActivity()).addPendingFoodItem(f);
		        	PendingFoodLab.get(getActivity()).savePendingFoods();
		        	
	            }
	        }
	    
}
