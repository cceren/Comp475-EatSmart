package edu.harding.android.eatsmart;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ConsumedFoodLab {
	private static final String FILENAME = "Consumedfood.json";
	private ArrayList<Food> mFoods;
	private EatSmartJSONSerializer mSerializer;

    private static ConsumedFoodLab sConsumedFoodLab;
    private Context mAppContext;

    private ConsumedFoodLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new EatSmartJSONSerializer(mAppContext, FILENAME);
        try{
        	mFoods = mSerializer.loadFoods("FILENAME");
        } catch (Exception e){
        	mFoods = new ArrayList<Food>();
        }
        
    }

    public static ConsumedFoodLab get(Context c) {
        if (sConsumedFoodLab == null) {
        	sConsumedFoodLab = new ConsumedFoodLab(c.getApplicationContext());
        }
        return sConsumedFoodLab;
    }

    public void addFoodItem(Food f) {
    	
    	if(!(mFoods.contains(f)))
    		mFoods.add(f);
    	
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
    		Log.d("ConsumedFoodLab", "Consumed foods saved to a file");
    		return true;
    	} catch(Exception e){
    		Log.e("FoodLab", "Error saving Consumed foods: ", e);
    		Toast.makeText(mAppContext, R.string.errorSaving_toast, Toast.LENGTH_SHORT).show();
    		return false;
    	}
    }
}
