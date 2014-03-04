package edu.harding.android.eatsmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

public class ProfileFragment extends Fragment {

	private final String USERINFO = "userInfo"; 
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		try{
			SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
	        String userName = sharedPreferences.getString("name", "");
	        if(userName != ""){
			FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            ft.replace(R.id.fragmentContainer, homeFragment).commit();
	        }
			
		}catch(Exception e){
			Log.e("ERROR", "Error loading the profile");
		}
	}
	
	public boolean saveProfile(){
		return true;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_profile, parent, false);
		
		final EditText nameEditText = (EditText)v.findViewById(R.id.nameEditText);
		final EditText age = (EditText)v.findViewById(R.id.age_editText);
		final EditText height = (EditText)v.findViewById(R.id.height_editText);
		final EditText weight = (EditText)v.findViewById(R.id.weight_editText);
		Button saveButton = (Button)v.findViewById(R.id.organize_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//saveProfile(); //Save the profile and go on to the main screen
				SharedPreferences preference = getActivity().getSharedPreferences(USERINFO, Context.MODE_PRIVATE);  
			    try{    
		        Editor editor = preference.edit();  
		        editor.putString("name", nameEditText.getText().toString());  
		        editor.putString("age", age.getText().toString());  
		        editor.putString("height", height.getText().toString());  
		        editor.putString("weight", weight.getText().toString());  
		        editor.commit(); 
		        preference.contains("name");
		        }catch(Exception e){  
		            Log.e("user information", "failed");
		        }
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
