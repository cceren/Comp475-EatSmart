package edu.harding.android.eatsmart;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import edu.harding.android.eatsmart.FoodDatabaseHelper.ConsumedFoodCursor;
import edu.harding.android.eatsmart.FoodDatabaseHelper.DayCursor;
import edu.harding.android.eatsmart.FoodDatabaseHelper.FoodCursor;
import edu.harding.android.eatsmart.FoodDatabaseHelper.PendingFoodCursor;

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
	
	public int getTotalCalories(){
		Date date = new Date();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        int currentDayId = getDay(currentDate);
        int totalCalories = mHelper.queryDayTotalCalories(currentDayId);
        Log.d(TAG, "Total calories = " + totalCalories);
        return totalCalories ;
	}
	
	public boolean incrementServing(long dayId, Food food){
		int totalCalories  = mHelper.queryDayTotalCalories(dayId);
		totalCalories += food.getCalories();
		
		int servingSize = mHelper.queryFoodServing(dayId, food.getTitle());
		mHelper.updateConsumedFood(dayId, ++servingSize, food.getTitle());
		mHelper.updateDayTotalCalories(dayId, totalCalories);
		
		return true;
	}
	
	public Day addDay(Day day){
		//Insert a day into the days table
		day.setId(mHelper.insertDay(day));
		return day;
	}
	
	public Food addConsumedFood(Food food, long dayId){
		
		int totalCalories  = mHelper.queryDayTotalCalories(dayId);
		//Log.d(TAG, "Total Calories " + totalCalories);
		totalCalories += food.getCalories();
		Log.d(TAG, "Total Calories " + totalCalories);
		//Insert an entry for a food in the consumedFood table. Last value indicates the serving, if new it is going to be one
		mHelper.insertConsumedFood(food, dayId, 1);
		mHelper.updateDayTotalCalories(dayId, totalCalories);
		
		return food;
	}
	
	public Food addPendingFood(Food food){
		mHelper.insertPendingFood(food);
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
	
	public Food getConsumedFood(long dayId, String foodName){
		Food food = null;
		FoodCursor cursor = mHelper.queryConsumedFood(dayId, foodName);
		cursor.moveToFirst();
		// If you got a row, get a food
		if(!cursor.isAfterLast())
			food = cursor.getFood();
		
		//Log.d(TAG, "Content of food: " + cursor.getString(1));
		cursor.close();
		//if(food == null)
			
		return food;
	}
	
	public int getDay(String date){

		Day day = null;
		int dayId = -1;
		DayCursor cursor = mHelper.queryDay(date);
		cursor.moveToFirst();
		//If you got a row, get a day
		if(!cursor.isAfterLast())
			//day = cursor.getDay();
			dayId = cursor.getInt(0);
		cursor.close();
		return dayId;
	}
	
	public ConsumedFoodCursor queryConsumedFoods(){
		return mHelper.queryConsumedFoods();
	}
	
	public PendingFoodCursor queryPendingFoods(){
		return mHelper.queryPendingFoods();
	}
	
	public Food getPendingFood(long id){
		return null;
	}
	
}
