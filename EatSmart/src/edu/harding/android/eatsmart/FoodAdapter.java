package edu.harding.android.eatsmart;

import java.util.List;



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

public class FoodAdapter extends BaseAdapter {

	private List<Food> listFood;
    private Context context;
	public static float a = 0;
	public static int totalCal;
    public FoodAdapter(Context context,List<Food> listFood){
    	this.context=context;
    	this.listFood=listFood;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFood.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listFood.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View convertView, ViewGroup arg2) {

		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.food_item_list, null);
		}
		final Food food=listFood.get(arg0);

		TextView foodname=(TextView) convertView.findViewById(R.id.food_item);
		TextView foodcal=(TextView) convertView.findViewById(R.id.calories_item);
		//CheckBox check = (CheckBox)convertView.findViewById(R.id.checkBox);
		TextView foodQuantity = (TextView)convertView.findViewById(R.id.quantityTextView);
		foodname.setText(food.getTitle());
		foodcal.setText(food.getCalories());
		
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				food.incrementQuantity();
				
			}
		});
		
		/*check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){ 
            @Override 
            public void onCheckedChanged(CompoundButton buttonView, 
                    boolean isChecked) { 
                // TODO Auto-generated method stub 
            	Log.e("cal", ""+food.getCalories());
                if(isChecked){ 
                	 totalCal = (food.getCalories());
                }else{ 
                	 totalCal = (food.getCalories());
                }    
                
                Log.e("totalCal", ""+totalCal);
            } 
        }); 
		*/
		return convertView;
	}
	
	public static int getTotalCal(){
		return totalCal;
		}


}
