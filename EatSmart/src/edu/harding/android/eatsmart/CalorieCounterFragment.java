package edu.harding.android.eatsmart;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class CalorieCounterFragment extends ListFragment {
	
	private ArrayList<Food> mFoodItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setTitle(R.string.crimes_title);
        mFoodItems = FoodLab.get(getActivity()).getFoods();
        FoodAdapter adapter = new FoodAdapter(mFoodItems);
       // ArrayAdapter<Food> adapter =
        	//	new ArrayAdapter<Food>(getActivity(),
        		//		android.R.layout.simple_list_item_1,
        		//		mFoodItems);
        setListAdapter(adapter);
        //setRetainInstance(true);
    }
    
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get the fooItem from the adapter
        Food f = ((FoodAdapter)getListAdapter()).getItem(position);
        f.incrementQuantity();
        ((FoodAdapter)getListAdapter()).notifyDataSetChanged();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
    	View v = super.onCreateView(inflater, parent, savedInstanceState);
    	return v;
    }

  

    private class FoodAdapter extends ArrayAdapter<Food> {
        public FoodAdapter(ArrayList<Food> foodItems) {
            super(getActivity(), android.R.layout.simple_list_item_1, foodItems);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.food_item_list, null);
            }

            // configure the view for this Crime
       
            Food f = getItem(position);
            TextView quantityTextView = (TextView)convertView.findViewById(R.id.quantity_text_view);
            quantityTextView.setText(f.getQuantity());
            
            TextView foodItemTextView = (TextView)convertView.findViewById(R.id.food_item_name_text_view);
            foodItemTextView.setText(f.getTitle());
            
            TextView caloriesTextView = (TextView)convertView.findViewById(R.id.calories_text_view);
            caloriesTextView.setText(f.getCalories());
             
            return convertView;
        }
    }
	
   

}
