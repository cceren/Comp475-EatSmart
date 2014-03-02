package edu.harding.android.eatsmart;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

	private static final String FILENAME = "foods.json";
	private EatSmartJSONSerializer mSerializer;
	private Profile mProfile;
	private Context mAppContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mAppContext = getActivity().getApplicationContext();
		mSerializer = new EatSmartJSONSerializer(mAppContext, FILENAME);
		try{
			//If there is a profile saved, load it and go on to the main screen
			mProfile = mSerializer.loadProfile();
			Log.e("JSON", "There was a profile saved: " + mProfile.getName());
			FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            ft.replace(R.id.fragmentContainer, homeFragment).commit();
			
		}catch(Exception e){
			mProfile = new Profile(); //If there is no profile saved, create a default one
			Log.e("ERROR", "Error loading the profile");
		}
	}
	
	public boolean saveProfile(){
		try{
			mSerializer.saveProfile(mProfile);
			return true;
		}catch(Exception e) {
			//Toast.makeText(mAppContext, R.string.errorSaving_toast, Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_profile, parent, false);
		
		EditText nameEditText = (EditText)v.findViewById(R.id.nameEditText);
		nameEditText.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(
					CharSequence c, int start, int before, int count){
				
			}
			public void beforeTextChanged(
					CharSequence c, int start, int count, int after){
				//intentional blank
			}
			public void afterTextChanged(Editable c){
				//intentional blank
				mProfile.setName(c.toString());
			}
		});
		Button saveButton = (Button)v.findViewById(R.id.organize_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveProfile(); //Save the profile and go on to the main screen
				FragmentManager fm = getActivity().getSupportFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();
				HomeFragment homeFragment = new HomeFragment();
				ft.replace(R.id.fragmentContainer, homeFragment).commit();
				//Toast.makeText(mAppContext, R.string.errorSaving_toast, Toast.LENGTH_SHORT).show();
			}
		});
		return v;
	}
	
}
