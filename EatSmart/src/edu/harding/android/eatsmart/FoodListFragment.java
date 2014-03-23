/**
 * This class is in charge of displaying the available foods
 * that the user can pick from
 */
package edu.harding.android.eatsmart;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
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
        //Add generic foods
        for(int i = 0; i < 50; i++){
			Food f = new Food();
			f.setTitle("Coconut Rice");
			f.setCalories(150);
			f.setQuantity(0);
			FoodManager.get(getActivity()).addFood(f);
		}
        //Initialize the loader to load the list of foods
        getLoaderManager().initLoader(0, null, this);
        Log.d(TAG, "Initialized loader");
        
    }

    

	public void onListItemClick(ListView l, View v, int position, long id) {
        // get the foodItem from the adapter
       Food f = FoodManager.get(getActivity()).getFood(id);
       
       //Add food to Days table
       //See if today's day is in the database
     
       //OR
       
       //If it is already in increase the serving size
        
       
        
        //Notify user he has added a food to history
        Context context = getActivity();
        CharSequence text = "Added  " + f.getTitle() + " to history";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        
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
            Food f = mFoodCursor.getFood();

            TextView foodNameTextView =
                (TextView)view.findViewById(R.id.consumed_food_item_name_text_view);
            foodNameTextView.setText(f.getTitle().toString());
            
            TextView foodQuantity =
                (TextView)view.findViewById(R.id.consumed_quantity_text_view);
            foodQuantity.setText((Integer.toString(f.getQuantity())) + " Servings");
            
            TextView foodCaloriesTextView =
                (TextView)view.findViewById(R.id.consumed_calories_text_view);
            foodCaloriesTextView.setText((Integer.toString(f.getCalories())) + " Cals");
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
}
