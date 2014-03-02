package edu.harding.android.eatsmart;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class FoodLab {
	private static final String FILENAME = "foods.json";
	private ArrayList<Food> mFoods;
	private EatSmartJSONSerializer mSerializer;

    private static FoodLab sFoodLab;
    private Context mAppContext;

    private FoodLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new EatSmartJSONSerializer(mAppContext, FILENAME);
        try{
        	mFoods = mSerializer.loadFoods("foods");
        } catch (Exception e){
        	mFoods = new ArrayList<Food>();
        }
        if(mFoods.size() < 1){
        	for (int i = 0; i < 50; i++) {
                Food f = new Food();
                f.setTitle("Food #" + i);
                f.setQuantity(0); 
                f.setCalories(i + 100);
                mFoods.add(f);
            }
        }
    }

    public static FoodLab get(Context c) {
        if (sFoodLab == null) {
        	sFoodLab = new FoodLab(c.getApplicationContext());
        }
        return sFoodLab;
    }

    public Food getFood(UUID id) {
        for (Food c : mFoods) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }
    
    public ArrayList<Food> getFoods() {
        return mFoods;
    }
    
    public boolean saveFoods(){
    	try{
    		mSerializer.saveFoods(mFoods, "foods");
    		Log.d("FoodLab", "foods saved to a file");
    		return true;
    	} catch(Exception e){
    		Log.e("FoodLab", "Error saving foods: ", e);
    		Toast.makeText(mAppContext, R.string.errorSaving_toast, Toast.LENGTH_SHORT).show();
    		return false;
    	}
    }
}
