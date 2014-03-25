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
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;

public class ProfileFragment extends Fragment {

	private final String USERINFO = "userInfo"; 
	private int monthOfYear=1;
	private int dayOfMonth=1 ;
	private int year=1992;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	public boolean saveProfile(){
		return true;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_profile, parent, false);
		
		final EditText nameEditText = (EditText)v.findViewById(R.id.nameEditText);
		
		final DatePicker datePicker=(DatePicker)v.findViewById(R.id.datePicker);
		final EditText height = (EditText)v.findViewById(R.id.height_editText);
		final EditText weight = (EditText)v.findViewById(R.id.weight_editText);
	    datePicker.init(1992, 0, 1, new OnDateChangedListener(){
	    public void onDateChanged(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
	           ProfileFragment.this.monthOfYear = monthOfYear+1;
	           ProfileFragment.this.dayOfMonth = dayOfMonth;
	           ProfileFragment.this.year = year;
	            }            
	        });
		Button saveButton = (Button)v.findViewById(R.id.organize_button);
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//saveProfile(); //Save the profile and go on to the main screen
				SharedPreferences preference = getActivity().getSharedPreferences(USERINFO, Context.MODE_PRIVATE);  
			    try{    
			        Editor editor = preference.edit();  
			        editor.putString("name", nameEditText.getText().toString());  
			        
			        editor.putString("birthday", ""+monthOfYear+"/"+dayOfMonth+"/"+year);
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
