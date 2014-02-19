package edu.harding.android.eatsmart;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
				//start an instance of CalorieCounterActivity
		        //Intent i = new Intent(getActivity(), CalorieCounterActivity.class);
		        //startActivity(i);
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                CalorieCounterFragment calorieCounterFragment = new CalorieCounterFragment();
                ft.replace(R.id.fragmentContainer, calorieCounterFragment).addToBackStack("ISa")
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
		return v;
	}
}
