/**
 * This class is in charge of displaying the available foods
 * that the user can pick from
 */
package edu.harding.android.eatsmart;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.harding.android.eatsmart.FoodDatabaseHelper.FoodCursor;
//import edu.harding.android.eatsmart.CalorieCounterFragment.FoodAdapter;

public class FoodListFragment extends ListFragment implements LoaderCallbacks<Cursor>{

	private static final String TAG = "FoodListFragment";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.foods_title);
        setHasOptionsMenu(true);
        //Initialize the loader to load the list of foods
        getLoaderManager().initLoader(0, null, this);
        
        
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity()) != null){
				getFragmentManager().popBackStack();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
        // get the foodItem from the adapter
      // Food f = FoodManager.get(getActivity()).getFood(id);
      // boolean foodAdded = addFoodToDatabase(f, id);
        
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
    }

    private class FoodCursorAdapter extends CursorAdapter {
    	private FoodCursor mFoodCursor;
        public FoodCursorAdapter(Context context, FoodCursor cursor) {
            super(context, cursor, 0);
            mFoodCursor = cursor;
        }
        
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
        	//Use a layout inflater to get a row view
        	LayoutInflater inflater = (LayoutInflater)context
        			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	return inflater
        			.inflate(R.layout.food_item_list, parent, false);
        }
        
        @Override
        public void bindView(View view, Context context, Cursor cursor){
        	//Get the food for the current row
        	 // configure the view for this Food Item
            final Food f = mFoodCursor.getFood();
            Date date = new Date();
            String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
            f.setDay(day);
            
            TextView nameTextView =
                (TextView)view.findViewById(R.id.food_name_textView1);
            nameTextView.setText(f.getTitle().toString());
            
            TextView servingSizeTextView =
                (TextView)view.findViewById(R.id.serving_size_textView);
            servingSizeTextView.setText((Integer.toString(f.getQuantity())) + " cups");
            
                
            TextView caloriesTextView =
                (TextView)view.findViewById(R.id.consumed_food_calories_textView);
            caloriesTextView.setText((Integer.toString(f.getCalories())) + " calories");
            
            ImageButton plusImageButton = 
            		(ImageButton)view.findViewById(R.id.plus_imageButton);
            
            plusImageButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					FoodManager.get(getActivity()).addFoodToDatabase(f);
					// Show Notification to user
					notifyUser(f);
				}
			});
        }
        
        
    }
    
    //This class is implemented in order to query the db Asynchronously
    private static class FoodListCursorLoader extends SQLiteCursorLoader{
    	public FoodListCursorLoader(Context context){
    		super(context);
    	}
    	
    	@Override
    	protected Cursor loadCursor(){
    		//Query the list of foods
    		return FoodManager.get(getContext()).queryFoods();
    		
    	}
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
    	//You only ever load the foods, so assume this is the case
    	return new FoodListCursorLoader(getActivity());
    }
    
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
    	//Create an adapter to point at this cursor
    	FoodCursorAdapter adapter = 
    			new FoodCursorAdapter(getActivity(), (FoodCursor)cursor);
    	setListAdapter(adapter);
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
    	//Stop using the cursor (via the adapter)
    	setListAdapter(null);
    }
    
    private boolean notifyUser(Food f){
   	 Context context = getActivity();
	        CharSequence text = "Decreased serving of  " + f.getTitle();
	        int duration = Toast.LENGTH_SHORT;
	        Toast toast = Toast.makeText(context, text, duration);
	        toast.show();
   	return true;
   }
  
}
