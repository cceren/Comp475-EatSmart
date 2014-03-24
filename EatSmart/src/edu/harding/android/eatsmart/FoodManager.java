package edu.harding.android.eatsmart;

import android.content.Context;
import edu.harding.android.eatsmart.FoodDatabaseHelper.ConsumedFoodCursor;
import edu.harding.android.eatsmart.FoodDatabaseHelper.FoodCursor;
import edu.harding.android.eatsmart.FoodDatabaseHelper.DayCursor;

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
		//Insert a food into the food table
		mHelper.insertFood(food);
		return food;
	}
	
	public Day addDay(Day day){
		//Insert a day into the days table
		day.setId(mHelper.insertDay(day));
		return day;
	}
	
	public Food addConsumedFood(Food food, long dayId){
		//Insert a day into the days table
		mHelper.insertConsumedFood(food, dayId);
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
	
	public Food getConsumedFood(long dayId, long foodId){
		Food food = null;
		FoodCursor cursor = mHelper.queryConsumedFood(dayId, foodId);
		cursor.moveToFirst();
		// If you got a row, get a run
		if(!cursor.isAfterLast())
			food = cursor.getFood();
		cursor.close();
		return food;
	}
	
	public Day getDay(String date){

		Day day = null;
		DayCursor cursor = mHelper.queryDay(date);
		cursor.moveToFirst();
		//If you got a row, get a day
		if(!cursor.isAfterLast())
			day = cursor.getDay();
		cursor.close();
		return day;
	}
	
	public ConsumedFoodCursor queryConsumedFoods(){
		return mHelper.queryConsumedFoods();
	}
}
