package edu.harding.android.eatsmart;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class SectionedListAdapter extends ArrayAdapter<Food> {

	
	private Context mContext;
	public SectionedListAdapter(Context context, int resource, ArrayList<Food> objects) {
		super(context, resource, objects);
		mContext = context;
		
	}
	
	 @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         // if we weren't given a view, inflate one
         if (convertView == null) {
             LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = inflater.inflate(R.layout.consumed_food_item_list, null);
         }
         
         boolean needSeparator = false;
        

         // configure the view for this consumed food Item
         Food currentFood = getItem(position); 
         TextView separator = (TextView)convertView.findViewById(R.id.separator);
    	 separator.setText(currentFood.getDate().toString());
    	 
         if(position == 0){
        	 needSeparator = true;
         }else {
        	 //If the item before the current is different
        	 Food previousFood = getItem(position - 1);
        	 
        	 if(!currentFood.getDate().toString()
        			 .equals(previousFood.getDate().toString())){
        		 needSeparator = true;
        	 }
         
         }
         
         if(needSeparator){
        	 //Make separator visible	
        	 separator.setVisibility(View.VISIBLE);
         }else{
        	 separator.setVisibility(View.GONE);
         }
         Log.e("Consumed", "Show ConsumedFood");
         TextView foodNameTextView =
             (TextView)convertView.findViewById(R.id.consumed_food_item_name_text_view);
         foodNameTextView.setText(currentFood.getTitle().toString());
         
         TextView foodQuantity =
             (TextView)convertView.findViewById(R.id.consumed_quantity_text_view);
         foodQuantity.setText(Integer.toString(currentFood.getQuantity()));
         
         TextView foodCaloriesTextView =
             (TextView)convertView.findViewById(R.id.consumed_calories_text_view);
         foodCaloriesTextView.setText(Integer.toString(currentFood.getCalories()));

         return convertView;
     }
     
	

}
