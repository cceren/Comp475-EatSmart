package edu.harding.android.eatsmart;
import java.util.ArrayList;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PendingFoodListFragment extends ListFragment {

	private ArrayList<Food> mPendingFoodItems;
	
	@Override public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		 
		 mPendingFoodItems = PendingFoodLab.get(getActivity()).getPendingFoodItems();
		 
		 PendingFoodAdapter adapter = new PendingFoodAdapter(mPendingFoodItems);
		 setListAdapter(adapter);
	}
	
	private class PendingFoodAdapter extends ArrayAdapter<Food> {
        public PendingFoodAdapter(ArrayList<Food> pendingFood) {
            super(getActivity(), android.R.layout.simple_list_item_1, pendingFood);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.food_item_list, null);
            }

            // configure the view for this Crime
            try{
                Food f = getItem(position);
                TextView quantityTextView = (TextView)convertView.findViewById(R.id.quantity_text_view);
                quantityTextView.setText(f.getQuantity());
                
                TextView foodItemTextView = (TextView)convertView.findViewById(R.id.food_item_name_text_view);
                foodItemTextView.setText(f.getTitle().toString());
                
                TextView caloriesTextView = (TextView)convertView.findViewById(R.id.calories_text_view);
                caloriesTextView.setText(f.getCalories());
                Log.e("getItem", "got A foodItem");
                Log.e("getItem", f.getTitle());
                
               }catch(Exception e){
            	   Log.e("getItem", e.toString());
               }
            
            return convertView;
        }
    }
}
