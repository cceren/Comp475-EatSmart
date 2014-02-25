package edu.harding.android.eatsmart;
import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;


public class PendingFoodLab {
	 private static final String TAG = "PendingFoodLab";
	   // private static final String FILENAME = "crimes.json";

	    private ArrayList<Food> mPendingFoods;
	    //private CriminalIntentJSONSerializer mSerializer;

	    private static PendingFoodLab sPendingFoodLab;
	    private Context mAppContext;

	    private PendingFoodLab(Context appContext) {
	        mAppContext = appContext;
	       /* mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);

	        try {
	            mCrimes = mSerializer.loadCrimes();
	        } catch (Exception e) {
	            mCrimes = new ArrayList<Crime>();
	            Log.e(TAG, "Error loading crimes: ", e);
	        }*/
	        mPendingFoods = new ArrayList<Food>();
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

	    /*public boolean saveCrimes() {
	        try {
	            mSerializer.saveCrimes(mCrimes);
	            Log.d(TAG, "crimes saved to file");
	            return true;
	        } catch (Exception e) {
	            Log.e(TAG, "Error saving crimes: " + e);
	            return false;
	        }
	    }*/
}
