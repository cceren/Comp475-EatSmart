package edu.harding.android.eatsmart;
import java.util.Calendar;

import edu.harding.android.eatsmart.R;
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
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView.OnEditorActionListener;


public class UpdateProfileFragment extends Fragment {
	private final String USERINFO = "userInfo"; 
	private String TAG = "UpdateProfileFragment";
	private int mMonth;
	private int mDay;
	private int mYear;
	private String mGender;
	private int mAge;
	private int mHeightInFeet;
	private int mHeightInInches;
	private int mWeight;
	private String mActivityLevel;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			if(getFragmentManager().getBackStackEntryCount() > 0){
				getFragmentManager().popBackStack();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_profile, parent, false);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		}
		
		final Spinner footSpinner = (Spinner) v.findViewById(R.id.ft_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
		        R.array.foot_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		footSpinner.setAdapter(adapter);
		
		final Spinner inchesSpinner = (Spinner) v.findViewById(R.id.in_spinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(v.getContext(),
		        R.array.inches_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		inchesSpinner.setAdapter(adapter2);
		
		final Spinner activityLevelSpinner = (Spinner) v.findViewById(R.id.activity_level_spinner);
		ArrayAdapter<CharSequence> activityLevelAdapter = ArrayAdapter.createFromResource(v.getContext(),
		        R.array.activity_levels_array, android.R.layout.simple_spinner_item);
		 // Specify the layout to use when the list of choices appears
		activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 // Apply the adapter to the spinner
		 activityLevelSpinner.setAdapter(activityLevelAdapter);
		
		final EditText nameEditText = (EditText)v.findViewById(R.id.nameEditText);
		final DatePicker datePicker=(DatePicker)v.findViewById(R.id.datePicker);
		
		final EditText weightEditText = (EditText)v.findViewById(R.id.weight_editText);
		final Button saveButton = (Button)v.findViewById(R.id.organize_button);
		
		
		//Check to see if there are saved values, if the user did not save anything, the fields will appear blank
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
		nameEditText.setText(sharedPreferences.getString("name", ""));
		weightEditText.setText(sharedPreferences.getString("weight", ""));
        footSpinner.setSelection(adapter.getPosition(sharedPreferences.getString("heightFt", "")));
        inchesSpinner.setSelection(adapter2.getPosition(sharedPreferences.getString("heightIn", "")));
        activityLevelSpinner.setSelection(activityLevelAdapter.getPosition(sharedPreferences.getString("activityLevel", "")));
        mGender = sharedPreferences.getString("gender", "");
        mMonth = Integer.parseInt(sharedPreferences.getString("month", ""));
        mYear = Integer.parseInt(sharedPreferences.getString("year", ""));
        mDay = Integer.parseInt(sharedPreferences.getString("day", ""));
      
        datePicker.updateDate(mYear, mMonth, mDay);
        weightEditText.setOnEditorActionListener(new OnEditorActionListener() {

	           @Override
	           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	               // TODO Auto-generated method stub
	               if (actionId == EditorInfo.IME_ACTION_DONE) {
	                   // do your stuff here
	            	   
	                   saveButton.callOnClick();
	               }
	               return false;
	           }
	       });
        Log.d(TAG, mYear + " " + mMonth + " " + mDay);
	    datePicker.init(mYear, mMonth, mDay, new OnDateChangedListener(){
	    public void onDateChanged(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
	           mMonth = monthOfYear;
	           mDay = dayOfMonth;
	           mYear = year;
	            }            
	        });
	    
	    RadioButton maleRadioButton = (RadioButton)v.findViewById(R.id.male_radio);
	    maleRadioButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mGender = "male";
			}
		});
	    
	    RadioButton femaleRadioButton = (RadioButton)v.findViewById(R.id.female_radio);
	    femaleRadioButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mGender = "female";
			}
		});
		
	    if(mGender.equals("male")){
        	maleRadioButton.setChecked(true);
        }else{
        	femaleRadioButton.setChecked(true);
        }
		
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
				    try{    
				        Editor editor = preference.edit();  
				        editor.putString("name", nameEditText.getText().toString());  
				        editor.putString("suggestedCalories", Integer.toString(suggestedCalorieIntake()));
				        editor.putString("activityLevel", mActivityLevel);
				        editor.putString("year", Integer.toString(mYear));
				        editor.putString("month", Integer.toString(mMonth));
				        editor.putString("day", Integer.toString(mDay));
				        editor.putString("heightFt", footSpinner.getSelectedItem().toString());
				        editor.putString("heightIn", inchesSpinner.getSelectedItem().toString());  
				        editor.putString("weight", weightEditText.getText().toString());  
				        editor.putString("gender", mGender);
				        editor.commit(); 
				        preference.contains("name");
			        }catch(Exception e){  
			            Log.e("user information", "failed");
			        }
						FragmentManager fm = getActivity().getSupportFragmentManager();
						fm.popBackStack();
						//Toast.makeText(mAppContext, R.string.errorSaving_toast, Toast.LENGTH_SHORT).show();
				}else
				{
					showAlert();
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
		double bmr = (12.7 * (mHeightInFeet*12 + mHeightInInches))
				+ (6.23 * mWeight) - (6.8 * mAge);
				
				bmr += 66;
		
		return bmr;
	}
	
	private double femaleBMR(){
		double bmr = (4.7 * (mHeightInFeet*12 + mHeightInInches))
				+ (4.35 * mWeight) - (4.7 * mAge);
				
				bmr += 656;
		
		return bmr;
	}
	
	private void showAlert(){
		new AlertDialog.Builder(getActivity())
	    .setTitle("Incomplete Form")
	    .setMessage("Please enter your weight (lb)")
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    
	     
	    .setIcon(android.R.drawable.ic_dialog_alert)
	     .show();
	}
}
