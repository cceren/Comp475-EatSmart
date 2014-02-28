package edu.harding.android.eatsmart;
import java.util.ArrayList;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
                    .inflate(R.layout.pending_food_item, null);
            }

            
            // configure the view for this Food Item
            Food f = getItem(position);

            TextView timeTextView =
                (TextView)convertView.findViewById(R.id.time_textView);
            timeTextView.setText( "Date: " + f.getDate().toString());
            
            ImageView foodView = (ImageView)convertView.findViewById(R.id.pendingFood_imageView);
            Photo p = f.getPhoto();
            BitmapDrawable b = null;
            if(p != null){
            	String path = getActivity()
            			.getFileStreamPath(p.getFilename()).getAbsolutePath();
            	b = PictureUtils.getScaledDrawable(getActivity(), path);
            }
            foodView.setImageDrawable(b);
            
            return convertView;
        }
    }
}
