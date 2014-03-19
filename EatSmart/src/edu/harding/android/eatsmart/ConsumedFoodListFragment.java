package edu.harding.android.eatsmart;

import java.util.ArrayList;


import android.os.Bundle;
import android.support.v4.app.ListFragment;

/*Added comment*/
public class ConsumedFoodListFragment extends ListFragment {
	private ArrayList<Food> mConsumedFoodItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.consumed_food);
        
        mConsumedFoodItems = ConsumedFoodLab.get(getActivity()).getFoods();
        SectionedListAdapter adapter = 
        		new SectionedListAdapter(getActivity(), 0,mConsumedFoodItems);
       
        setListAdapter(adapter);
    }
    
    @Override
   	public void onPause() {
   		super.onPause();
   		ConsumedFoodLab.get(getActivity()).saveFoods();
   	}

}
