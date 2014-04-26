package edu.harding.android.eatsmart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import edu.harding.android.eatsmart.FoodDatabaseHelper.PendingFoodCursor;

public class PendingFoodListFragment extends ListFragment implements LoaderCallbacks<Cursor>  {

	private static final String TAG = "PendingFoodListFragment";
	private static final int SAVE_PENDING_FOOD = 1;
	
	@Override public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		  getActivity().setTitle(R.string.organize_quick_picks);
	        setHasOptionsMenu(true);

		  //Initialize the loader to load the list of foods
	        getLoaderManager().initLoader(0, null, this);
	        Log.d(TAG, "Initialized loader");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity()) != null){
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	
	public void onListItemClick(ListView l, View v, int position, long id) {
        
       Food pendingFood = FoodManager.get(getActivity()).getPendingFood(id);
       String path = pendingFood.getPhotoFilename();
       String date = pendingFood.getDay();
       String time = pendingFood.getTime();
       
       Intent i = new Intent(getActivity(), OrganizePendingFoodActivity.class);
       Bundle bundle = new Bundle();
       
       bundle.putString("filename", path);
       bundle.putLong("id", id);
       bundle.putString("date", date);
       bundle.putString("time", time);
       
       i.putExtras(bundle);
       startActivityForResult(i, SAVE_PENDING_FOOD);
       
      //Launch OrganizePendingFoodFragment HERE***
        
    }
	
	void setEmptyView(){
    	this.setEmptyText("All foods have been logged!");
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
	            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
	            Date date = null;
	            try {
					 date = format.parse(f.getPhotoDate() +",00:00");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            CharSequence dateFormat = DateFormat.format("EEEE, MMM dd, yyyy", date);
	    		//CharSequence timeFormat = DateFormat.format("h:mm a", date);
	    		
	            TextView dateTextView =
	                (TextView)view.findViewById(R.id.date_textView);
	            dateTextView.setText( "Date: " + dateFormat.toString());
	            TextView timeTextView =
	                    (TextView)view.findViewById(R.id.time_textView);
	            timeTextView.setText( "Time: " + f.getTime());
	            
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
		    	setEmptyView();
		    }
		    
		    @Override
		    public void onLoaderReset(Loader<Cursor> loader){
		    	//Stop using the cursor (via the adapter)
		    	setListAdapter(null);
		    }

			@Override
			public void onActivityResult(int requestCode, int resultCode,
					Intent data) {
				// TODO Auto-generated method stub
				super.onActivityResult(requestCode, resultCode, data);
				if (resultCode != Activity.RESULT_OK) return;
		        
				if (requestCode == SAVE_PENDING_FOOD) {
		        	//Update the listView
		            getLoaderManager().restartLoader(0, null, this);
		        }
			}

			
		    
		    
}
