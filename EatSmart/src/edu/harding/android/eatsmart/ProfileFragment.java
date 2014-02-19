package edu.harding.android.eatsmart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ProfileFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_profile, parent, false);
		
		EditText nameEditText = (EditText)v.findViewById(R.id.nameEditText);
		
		Button saveButton = (Button)v.findViewById(R.id.organize_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
		
			
			
			@Override
			public void onClick(View v) {
				// save profile and start the mainFragment
				//UserProfile.get(getActivity()).setName(nameEditText.getText().toString());
				
				  FragmentManager fm = getActivity().getSupportFragmentManager();
	                FragmentTransaction ft = fm.beginTransaction();
	                HomeFragment homeFragment = new HomeFragment();
	                ft.replace(R.id.fragmentContainer, homeFragment).commit();
			}
		});
		return v;
	}
	
}
