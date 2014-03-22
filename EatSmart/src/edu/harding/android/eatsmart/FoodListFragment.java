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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import edu.harding.android.eatsmart.CalorieCounterFragment.FoodAdapter;

public class FoodListFragment extends ListFragment implements LoaderCallbacks<Cursor>{
	
	private ArrayList<Food> mFoods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.foods_title);
       //Initialize the loader to load the list of foods
        getLoaderManager().initLoader(0, null, this);
        
    }

    

	public void onListItemClick(ListView l, View v, int position, long id) {
        // get the foodItem from the adapter
        Food f = ((FoodAdapter)getListAdapter()).getItem(position);
        ConsumedFoodLab.get(getActivity()).addFoodItem(f);
        
        f.incrementQuantity();
        ((FoodAdapter)getListAdapter()).notifyDataSetChanged();
        
        //Notify user he has added a food to history
        Context context = getActivity();
        CharSequence text = "Added  " + f.getTitle() + " to history";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((FoodAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class FoodAdapter extends ArrayAdapter<Food> {
        public FoodAdapter(ArrayList<Food> foods) {
            super(getActivity(), android.R.layout.simple_list_item_1, foods);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.food_item_list, null);
            }

            // configure the view for this Food Item
            Food f = getItem(position);

            TextView foodNameTextView =
                (TextView)convertView.findViewById(R.id.consumed_food_item_name_text_view);
            foodNameTextView.setText(f.getTitle().toString());
            
            TextView foodQuantity =
                (TextView)convertView.findViewById(R.id.consumed_quantity_text_view);
            foodQuantity.setText((Integer.toString(f.getQuantity())) + " Servings");
            
            TextView foodCaloriesTextView =
                (TextView)convertView.findViewById(R.id.consumed_calories_text_view);
            foodCaloriesTextView.setText((Integer.toString(f.getCalories())) + " Cals");

            return convertView;
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
    }
    
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
    	//Stop using the cursor (via the adapter)
    	setListAdapter(null);
    }
}
