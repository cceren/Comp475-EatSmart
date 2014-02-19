package edu.harding.android.eatsmart;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class FoodLab {
	private ArrayList<Food> mFoods;

    private static FoodLab sFoodLab;
    private Context mAppContext;

    private FoodLab(Context appContext) {
        mAppContext = appContext;
        mFoods = new ArrayList<Food>();
        for (int i = 0; i < 100; i++) {
            Food f = new Food();
            f.setTitle("Food #" + i);
            f.setQuantity(0); // every other one
            f.setCalories(i + 100);
            mFoods.add(f);
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
}
