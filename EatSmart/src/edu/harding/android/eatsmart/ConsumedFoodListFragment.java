package edu.harding.android.eatsmart;
/***This fragment displays the foods the user has consumed
 * 
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.harding.android.eatsmart.FoodDatabaseHelper.ConsumedFoodCursor;
import edu.harding.android.eatsmart.FoodDatabaseHelper.FoodCursor;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class ConsumedFoodListFragment extends ListFragment implements LoaderCallbacks<Cursor> {
	

private static final String TAG = "ConsumedFoodListFragment";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.consumed_food);
      
		
        //Initialize the loader to load the list of foods
        getLoaderManager().initLoader(0, null, this);
        Log.d(TAG, "Initialized loader");
        
    }

    private class ConsumedFoodCursorAdapter extends CursorAdapter {
    	private ConsumedFoodCursor mConsumedFoodCursor;
        public ConsumedFoodCursorAdapter(Context context, ConsumedFoodCursor cursor) {
            super(context, cursor, 0);
            mConsumedFoodCursor = cursor;
        }
        
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
        	//Use a layout inflater to get a row view
        	LayoutInflater inflater = (LayoutInflater)context
        			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	return inflater
        			.inflate(R.layout.consumed_food_item_list, parent, false);
        }
        
        @Override
        public void bindView(View view, Context context, Cursor cursor){
        	//Get the food for the current row
        	 // configure the view for this Food Item
        	final  Food f = mConsumedFoodCursor.getFood();

            Log.d(TAG, String.valueOf(f.getQuantity()));
            
            TextView foodNameTextView =
                (TextView)view.findViewById(R.id.food_name_textView);
            foodNameTextView.setText(f.getTitle().toString());
            
            TextView foodQuantity =
                (TextView)view.findViewById(R.id.servingsAmount_textView);
            foodQuantity.setText((Integer.toString(f.getQuantity())) + " Servings");
            
            TextView foodCaloriesTextView =
                (TextView)view.findViewById(R.id.consumed_food_calories_textView);
            foodCaloriesTextView.setText((Integer.toString(f.getCalories())) + " calories");
            
            ImageButton minusImageButton = 
            		(ImageButton)view.findViewById(R.id.minus_imageButton);
            
            minusImageButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					FoodManager.get(getActivity()).decreaseConsumedFoodServing(f);
					// Show Notification to user
					 Context context = getActivity();
				        CharSequence text = "Decreased serving of  " + f.getTitle();
				        int duration = Toast.LENGTH_SHORT;
				        Toast toast = Toast.makeText(context, text, duration);
				        toast.show();
				    //refresh loaders
				        mConsumedFoodCursor.requery();
				}
			});
        }
        
        
    }
    
    //This class is implemented in order to query the database Asynchronously
    private static class ConsumedFoodListCursorLoader extends SQLiteCursorLoader{
    	public ConsumedFoodListCursorLoader(Context context){
    		super(context);
    	}
    	
    	@Override
    	protected Cursor loadCursor(){
    		//Query the list of consumed Foods for the current day
    		  Date date = new Date();
    	      String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(date);
    		return FoodManager.get(getContext()).queryConsumedFoods(currentDay);
    		
    	}
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
    	//You only ever load the foods, so assume this is the case
    	return new ConsumedFoodListCursorLoader(getActivity());
    }
    
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
    	//Create an adapter to point at this cursor
    	ConsumedFoodCursorAdapter adapter = 
    			new ConsumedFoodCursorAdapter(getActivity(), (ConsumedFoodCursor)cursor);
    	setListAdapter(adapter);
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
    	//Stop using the cursor (via the adapter)
    	setListAdapter(null);
    }
   
}
