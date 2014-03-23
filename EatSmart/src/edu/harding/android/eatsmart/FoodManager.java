package edu.harding.android.eatsmart;

import edu.harding.android.eatsmart.FoodDatabaseHelper.FoodCursor;
import android.content.Context;

public class FoodManager {
	private static final String TAG = "FoodManager";
	
	private static FoodManager sFoodManager;
	private Context mAppContext;
	private FoodDatabaseHelper mHelper;
	
	//The private constructor forces the users to use FoodManager.get(Context)
	private FoodManager(Context appContext){
		mAppContext = appContext;
		mHelper = new FoodDatabaseHelper(mAppContext);
	}
	
	public static FoodManager get(Context c){
		if(sFoodManager == null){
			//Use the application context to avoid leaking activities
			sFoodManager = new FoodManager(c.getApplicationContext());
		}
		return sFoodManager;
	}
	
	public Food addFood(Food food){
		//Insert a food into the db
		mHelper.insertFood(food);
		return food;
	}
	
	public FoodCursor queryFoods(){
		return mHelper.queryFoods();
	}
	
	public Food getFood(long id){
		Food food = null;
		FoodCursor cursor = mHelper.queryFood(id);
		cursor.moveToFirst();
		// If you got a row, get a run
		if(!cursor.isAfterLast())
			food = cursor.getFood();
		cursor.close();
		return food;
	}
}
