package edu.harding.android.eatsmart;
import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class PendingFoodLab {
	 private static final String TAG = "PendingFoodLab";
	 private static final String FILENAME = "foods.json";

	    private ArrayList<Food> mPendingFoods;
	    private EatSmartJSONSerializer mSerializer;

	    private static PendingFoodLab sPendingFoodLab;
	    private Context mAppContext;

	    private PendingFoodLab(Context appContext) {
	       mAppContext = appContext;
	       mSerializer = new EatSmartJSONSerializer(mAppContext, FILENAME);

	        try {
	            mPendingFoods = mSerializer.loadFoods("pending");
	        } catch (Exception e) {
	        	mPendingFoods = new ArrayList<Food>();
	            Log.e(TAG, "Error loading Foods: ", e);
	        }
	    }

	    public static PendingFoodLab get(Context c) {
	        if (sPendingFoodLab == null) {
	        	sPendingFoodLab = new PendingFoodLab(c.getApplicationContext());
	        }
	        return sPendingFoodLab;
	    }

	    public Food getFood(UUID id) {
	        for (Food f : mPendingFoods) {
	            if (f.getId().equals(id))
	                return f;
	        }
	        return null;
	    }
	    
	    public void addPendingFoodItem(Food f) {
	    	mPendingFoods.add(f);
	        //saveCrimes();
	    }

	    public ArrayList<Food> getPendingFoodItems() {
	        return mPendingFoods;
	    }

	    public void deleteFood(Food c) {
	    	mPendingFoods.remove(c);
	        //saveCrimes();
	    }

	    public boolean savePendingFoods(){
	    	try{
	    		mSerializer.saveFoods(mPendingFoods, "pending");
	    		return true;
	    	} catch(Exception e){
	    		Log.e("PendingFoodLab", "Error saving: ", e);
	    		Toast.makeText(mAppContext, R.string.errorSaving_toast, Toast.LENGTH_SHORT).show();
	    		return false;
	    	}
	    }
}
