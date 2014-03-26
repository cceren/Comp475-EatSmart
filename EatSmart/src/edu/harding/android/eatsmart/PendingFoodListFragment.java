package edu.harding.android.eatsmart;

import edu.harding.android.eatsmart.FoodDatabaseHelper.PendingFoodCursor;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PendingFoodListFragment extends ListFragment implements LoaderCallbacks<Cursor>  {

	private static final String TAG = "PendingFoodListFragment";

	
	@Override public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		  getActivity().setTitle(R.string.organize_quick_picks);
		  
		  //Initialize the loader to load the list of foods
	        getLoaderManager().initLoader(0, null, this);
	        Log.d(TAG, "Initialized loader");
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
        
       Food food = FoodManager.get(getActivity()).getPendingFood(id);
       
   
       
    
      //Launch OrganizePendingFoodFragment HERE***
        
    }

	private class PendingFoodCursorAdapter  extends CursorAdapter {
		private PendingFoodCursor mPendingFoodCursor;
		public PendingFoodCursorAdapter(Context context, PendingFoodCursor cursor) {
            super(context, cursor, 0);
            mPendingFoodCursor = cursor;
        }
		
		 @Override
	        public View newView(Context context, Cursor cursor, ViewGroup parent){
	        	//Use a layout inflater to get a row view
	        	LayoutInflater inflater = (LayoutInflater)context
	        			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	return inflater
	        			.inflate(R.layout.pending_food_item, parent, false);
	        }
		 
		 @Override
		 public void bindView(View view, Context context, Cursor cursor){
			// if we weren't given a view, inflate one
	            if (null == view) {
	            	view = getActivity().getLayoutInflater()
	                    .inflate(R.layout.pending_food_item, null);
	            }
	            // configure the view for this Food Item
	            Food f = mPendingFoodCursor.getFood();

	            //define formats in order to display the timestamp more clearly
	            CharSequence dateFormat = DateFormat.format("EEEE, MMM dd, yyyy", f.getDate());
	    		CharSequence timeFormat = DateFormat.format("h:mm a", f.getDate());
	    		
	            TextView dateTextView =
	                (TextView)view.findViewById(R.id.date_textView);
	            dateTextView.setText( "Date: " + dateFormat.toString());
	            TextView timeTextView =
	                    (TextView)view.findViewById(R.id.time_textView);
	            timeTextView.setText( "Time: " + timeFormat.toString());
	            
	            ImageView foodView = (ImageView)view.findViewById(R.id.pendingFood_imageView);
	            Photo p = f.getPhoto();
	            BitmapDrawable b = null;
	            if(p != null){
	            	String path = getActivity()
	            			.getFileStreamPath(p.getFilename()).getAbsolutePath();
	            	b = PictureUtils.getScaledDrawable(getActivity(), path);
	            }
	            foodView.setImageDrawable(b);
		 }
	}
		 //This class is implemented in order to query the database Asynchronously
		    private static class PendingFoodListCursorLoader extends SQLiteCursorLoader{
		    	public PendingFoodListCursorLoader(Context context){
		    		super(context);
		    	}
		    	
		    	@Override
		    	protected Cursor loadCursor(){
		    		//Query the list of consumed Foods
		    		return FoodManager.get(getContext()).queryPendingFoods();
		    		
		    	}
		    }

		    @Override
		    public Loader<Cursor> onCreateLoader(int id, Bundle args){
		    	//You only ever load the foods, so assume this is the case
		    	return new PendingFoodListCursorLoader(getActivity());
		    }
		    
		    @Override
		    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
		    	//Create an adapter to point at this cursor
		    	PendingFoodCursorAdapter adapter = 
		    			new PendingFoodCursorAdapter(getActivity(), (PendingFoodCursor)cursor);
		    	setListAdapter(adapter);
		    }
		    
		    @Override
		    public void onLoaderReset(Loader<Cursor> loader){
		    	//Stop using the cursor (via the adapter)
		    	setListAdapter(null);
		    }
		
		    
}
