package edu.harding.android.eatsmart;

import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ProfileFragment extends Fragment {

	private final String USERINFO = "userInfo"; 
	private int mMonth = 1;
	private int mDay = 1 ;
	private int mYear = 1992;
	private String mGender;
	private int mAge;
	private int mHeightInFeet;
	private int mHeightInInches;
	private int mWeight;
	private String mActivityLevel;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		mGender = "male";
	}
	
	public boolean saveProfile(){
		return true;
	}

	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		final View v = inflater.inflate(R.layout.fragment_profile, parent, false);
		final Spinner footSpinner = (Spinner) v.findViewById(R.id.ft_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
		        R.array.foot_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		footSpinner.setAdapter(adapter);
		footSpinner.setSelection(1);
		
		final Spinner inchesSpinner = (Spinner) v.findViewById(R.id.in_spinner);
		adapter = ArrayAdapter.createFromResource(v.getContext(),
		        R.array.inches_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inchesSpinner.setAdapter(adapter);
		inchesSpinner.setSelection(6);
		

	    final Spinner activityLevelSpinner = (Spinner) v.findViewById(R.id.activity_level_spinner);
		 // Create an ArrayAdapter using the string array and a default spinner layout
		 //ArrayAdapter<CharSequence> activityLevelsAdapter = ArrayAdapter.createFromResource(v.getContext(),
		        // R.array.activity_levels_array, android.R.layout.simple_spinner_item);
	    adapter = ArrayAdapter.createFromResource(v.getContext(),
		        R.array.activity_levels_array, android.R.layout.simple_spinner_item);
	    
		 // Specify the layout to use when the list of choices appears
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 // Apply the adapter to the spinner
		 activityLevelSpinner.setAdapter(adapter);
		 activityLevelSpinner.setSelection(2);
		 
		final EditText nameEditText = (EditText)v.findViewById(R.id.nameEditText);
		
		final DatePicker datePicker=(DatePicker)v.findViewById(R.id.datePicker);
		final EditText weightEditText = (EditText)v.findViewById(R.id.weight_editText);
		final Button saveButton = (Button)v.findViewById(R.id.organize_button);
		weightEditText.setOnEditorActionListener(new OnEditorActionListener() {

	           @Override
	           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	               // TODO Auto-generated method stub
	               if (actionId == EditorInfo.IME_ACTION_DONE) {
	                   // do your stuff here
	            	   InputMethodManager inputManager = (InputMethodManager)
		         			   v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
		
		         	   inputManager.hideSoftInputFromWindow(weightEditText.getWindowToken(),
		                           InputMethodManager.HIDE_NOT_ALWAYS);
	               }
	               return false;
	           }
	       });
     
     nameEditText.setOnEditorActionListener(new OnEditorActionListener() {

	           @Override
	           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	               // TODO Auto-generated method stub
	               if (actionId == EditorInfo.IME_ACTION_NEXT) {
	                   // do your stuff here
	            	   InputMethodManager inputManager = (InputMethodManager)
		         			   v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
		
		         	   inputManager.hideSoftInputFromWindow(weightEditText.getWindowToken(),
		                           InputMethodManager.HIDE_NOT_ALWAYS);
	               }
	               return false;
	           }
	       });
	    datePicker.init(1992, 0, 1, new OnDateChangedListener(){
	    public void onDateChanged(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
	           mMonth = monthOfYear+1;
	           mDay = dayOfMonth;
	           mYear = year;
	            }            
	        });
	    
	    final RadioButton maleRadioButton = (RadioButton)v.findViewById(R.id.male_radio);
	    maleRadioButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mGender = "male";
				
			}
		});
	    
	    final RadioButton femaleRadioButton = (RadioButton)v.findViewById(R.id.female_radio);
	    femaleRadioButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mGender = "female";
			}
		});
		
	  
	   
		//Save information of User
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(weightEditText.getText().toString().length() > 1){
					//saveProfile(); //Save the profile and go on to the main screen
					mHeightInFeet = (Integer) Integer.parseInt(footSpinner.getSelectedItem().toString());
					mHeightInInches = (Integer)Integer.parseInt(inchesSpinner.getSelectedItem().toString());
					
					Calendar calendar = Calendar.getInstance();
					mAge = mYear - calendar.get(Calendar.YEAR);
					mWeight = Integer.parseInt(weightEditText.getText().toString());
					mActivityLevel = activityLevelSpinner.getSelectedItem().toString();
					
					
					InputMethodManager inputManager = (InputMethodManager)
	         			   v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
	
	         	   inputManager.hideSoftInputFromWindow(weightEditText.getWindowToken(),
	                           InputMethodManager.HIDE_NOT_ALWAYS);
					
					SharedPreferences preference = getActivity().getSharedPreferences(USERINFO, Context.MODE_PRIVATE);  
					if(maleRadioButton.isChecked())
						mGender = "male";
					else if(femaleRadioButton.isChecked())
						mGender = "female";
					
				    try{    
				        Editor editor = preference.edit();  
				        editor.putString("name", nameEditText.getText().toString());  
				        editor.putString("suggestedCalories", Integer.toString(suggestedCalorieIntake()));
				        editor.putString("activityLevel", mActivityLevel);
				        editor.putString("gender", mGender);
				        editor.putString("year", Integer.toString(mYear));
				        editor.putString("month", Integer.toString(mMonth));
				        editor.putString("day", Integer.toString(mDay));
				        editor.putString("heightFt", footSpinner.getSelectedItem().toString());
				        editor.putString("heightIn", inchesSpinner.getSelectedItem().toString());  
				        editor.putString("weight", weightEditText.getText().toString());  
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
				}else
				{
					if(weightEditText.getText().toString().length() < 1 &&
						nameEditText.getText().toString().length() < 1){
						showAlert("Please enter your name and weight (lb)");
					}else if(weightEditText.getText().toString().length() < 1){
						showAlert("Please enter your weight (lb)");
					}else if(nameEditText.getText().toString().length() < 1){
						showAlert("Please enter your name");
					}
					
				}
			}
		});
		
		return v;
	
	}
	

	
	private int suggestedCalorieIntake(){
		double calorieIntake = 0;
		double bmr;
		if(mGender.equals("male")){
			bmr = maleBMR();
		}
		else{
			bmr = femaleBMR();
		}
		
		if(mActivityLevel.equals("I exercise little")){
			calorieIntake = bmr * 1.2;
		}else if(mActivityLevel.equals("I do light exercise")){
			calorieIntake = bmr * 1.375;
		}else if(mActivityLevel.equals("I exercise moderately")){
			calorieIntake = bmr * 1.55;
		}else if(mActivityLevel.equals("I exercise very often")){
			calorieIntake = bmr * 1.725;
		}else if(mActivityLevel.equals("I exercise hard core!")){
			calorieIntake = bmr * 1.9;
		}
		
		return (int) calorieIntake;
	}
	
	private double maleBMR(){
		double bmr = (12.7 * (mHeightInFeet*(12) + mHeightInInches))
				+ (6.23 * mWeight) - (6.8 * mAge);
				
				bmr += 66;
		//BMR = 66 + ( 6.23 x weight in pounds ) + ( 12.7 x height in inches ) 
		//		- ( 6.8 x age in year )
		
		return bmr;
	}
	
	private double femaleBMR(){
		double bmr = (4.7 * (mHeightInFeet*12 + mHeightInInches))
				+ (4.35 * mWeight) - (4.7 * mAge);
				
				bmr += 655;
		//Women: BMR = 655 + ( 4.35 x weight in pounds ) + ( 4.7 x height in inches )
				// - ( 4.7 x age in years )
		return bmr;
	}
	
	private void showAlert(String message){
		new AlertDialog.Builder(getActivity())
	    .setTitle("Incomplete Form")
	    .setMessage(message)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    
	     
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
}
