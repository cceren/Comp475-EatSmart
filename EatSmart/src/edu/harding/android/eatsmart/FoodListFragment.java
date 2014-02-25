package edu.harding.android.eatsmart;

import java.util.ArrayList;

//import edu.harding.android.eatsmart.CalorieCounterFragment.FoodAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FoodListFragment extends ListFragment {
	private ArrayList<Food> mFoods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.foods_title);
        mFoods = FoodLab.get(getActivity()).getFoods();
        FoodAdapter adapter = new FoodAdapter(mFoods);
        setListAdapter(adapter);
    }

    /*public void onListItemClick(ListView l, View v, int position, long id) {
        Food c = ((FoodAdapter)getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), FoodActivity.class);
        i.putExtra(FoodFragment.EXTRA_FOOD_ID, c.getId());
        startActivityForResult(i, 0);
    }*/
    
    @Override
	public void onPause() {
		super.onPause();
		FoodLab.get(getActivity()).saveFoods();
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
        // get the fooItem from the adapter
        Food f = ((FoodAdapter)getListAdapter()).getItem(position);
        f.incrementQuantity();
        ((FoodAdapter)getListAdapter()).notifyDataSetChanged();
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

            // configure the view for this Crime
            Food f = getItem(position);

            TextView foodNameTextView =
                (TextView)convertView.findViewById(R.id.food_item_name_text_view);
            foodNameTextView.setText(f.getTitle().toString());
            
            TextView foodQuantity =
                (TextView)convertView.findViewById(R.id.quantity_text_view);
            foodQuantity.setText(Integer.toString(f.getQuantity()));
            
            TextView foodCaloriesTextView =
                (TextView)convertView.findViewById(R.id.calories_text_view);
            foodCaloriesTextView.setText(Integer.toString(f.getCalories()));

            return convertView;
        }
        
    }
}
