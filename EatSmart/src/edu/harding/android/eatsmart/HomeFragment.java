package edu.harding.android.eatsmart;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

	private static final int REQUEST_PHOTO = 1;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		
		View v = inflater.inflate(R.layout.fragment_home, parent, false);
		
		Button addFoodButton = (Button)v.findViewById(R.id.add_food_button);
		addFoodButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FoodListFragment foodListFragment = new FoodListFragment();
                ft.replace(R.id.fragmentContainer, foodListFragment).addToBackStack("ISa")
                .commit();            
			}
		});
		
		
		Button quickPickButton = (Button)v.findViewById(R.id.quick_pick_button);
		quickPickButton.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), FoodCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
				
			}
		});
		
		
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
		            //Add a pendingFoodItem to foodItemLab
		        	Food f = new Food();
		        	f.setTitle("Pending");
		        	f.setCalories(150);
		        	f.setQuantity(2);
		        
		        	PendingFoodLab.get(getActivity()).addPendingFoodItem(f);
		        	Log.e("getItem", "Added Item");
	            }
	        }
	    
}
