package edu.harding.android.eatsmart;
import edu.harding.android.eatsmart.R;
import android.annotation.TargetApi;
import android.content.Context;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView.OnEditorActionListener;


public class UpdateProfileFragment extends Fragment {
	private final String USERINFO = "userInfo"; 
	private int monthOfYear=1;
	private int dayOfMonth=1 ;
	private int year=1992;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
	}
	
	
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstance){
		View v = inflater.inflate(R.layout.fragment_profile, parent, false);
		
		
		final EditText nameEditText = (EditText)v.findViewById(R.id.nameEditText);
		final DatePicker datePicker=(DatePicker)v.findViewById(R.id.datePicker);
		final EditText height = (EditText)v.findViewById(R.id.height_editText);
		final EditText weight = (EditText)v.findViewById(R.id.weight_editText);
		final Button saveButton = (Button)v.findViewById(R.id.organize_button);
		
		//Check to see if there are saved values, if the user did not save anything, the fields will appear blank
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
		nameEditText.setText(sharedPreferences.getString("name", ""));
        weight.setText(sharedPreferences.getString("weight", ""));
        height.setText(sharedPreferences.getString("height", ""));
        
		weight.setOnEditorActionListener(new OnEditorActionListener() {

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
	    datePicker.init(1992, 0, 1, new OnDateChangedListener(){
	    public void onDateChanged(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
	           UpdateProfileFragment.this.monthOfYear = monthOfYear+1;
	           UpdateProfileFragment.this.dayOfMonth = dayOfMonth;
	           UpdateProfileFragment.this.year = year;
	            }            
	        });
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				InputMethodManager inputManager = (InputMethodManager)
         			   v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 

         	   inputManager.hideSoftInputFromWindow(weight.getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
				
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
					HomeFragment homeFragment = new HomeFragment();
					fm.popBackStack();
					FragmentTransaction ft = fm.beginTransaction();
					ft.replace(R.id.fragmentContainer, homeFragment).commit();
			}
		});
		return v;
	}
}
