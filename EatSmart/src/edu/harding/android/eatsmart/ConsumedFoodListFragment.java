package edu.harding.android.eatsmart;
/***This fragment displays the foods the user has consumed
 * 
 */
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
import android.widget.TextView;


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
        			.inflate(R.layout.food_item_list, parent, false);
        }
        
        @Override
        public void bindView(View view, Context context, Cursor cursor){
        	//Get the food for the current row
        	 // configure the view for this Food Item
            Food f = mConsumedFoodCursor.getFood();

            Log.d(TAG, String.valueOf(f.getQuantity()));
            
            TextView foodNameTextView =
                (TextView)view.findViewById(R.id.consumed_food_item_name_text_view);
            foodNameTextView.setText(f.getTitle().toString());
            
            TextView foodQuantity =
                (TextView)view.findViewById(R.id.consumed_quantity_text_view);
            foodQuantity.setText((Integer.toString(f.getQuantity())) + " Servings");
            
            TextView foodCaloriesTextView =
                (TextView)view.findViewById(R.id.consumed_calories_text_view);
            foodCaloriesTextView.setText((Integer.toString(f.getCalories())) + " calories");
        }
        
        
    }
    
    //This class is implemented in order to query the database Asynchronously
    private static class ConsumedFoodListCursorLoader extends SQLiteCursorLoader{
    	public ConsumedFoodListCursorLoader(Context context){
    		super(context);
    	}
    	
    	@Override
    	protected Cursor loadCursor(){
    		//Query the list of consumed Foods
    		return FoodManager.get(getContext()).queryConsumedFoods();
    		
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
