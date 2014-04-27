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

		//Attempt to update the food
		//if it didn't update anything that means that food didn't exist
		//therefore, we need to insert a new entry
		if(mHelper.updateConsumedFood(dayId, food.getQuantity(), food.getTitle())
			<= 0){
			mHelper.insertConsumedFood(food, dayId, food.getQuantity(), food.getDay());
		}
		
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
	
	public Food getConsumedFood(String day, String foodName){
		Food food = null;
		ConsumedFoodCursor cursor = mHelper.queryConsumedFood(day, foodName);
		
		if(cursor != null){
			cursor.moveToFirst();
			// If you got a row, get a food
			if(!cursor.isAfterLast())
				food = cursor.getFood();
			
			//Log.d(TAG, "Content of food: " + cursor.getString(1));
			cursor.close();
			//if(food == null)
		}
			
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
	
	public ConsumedFoodCursor queryConsumedFoods(String day){
		return mHelper.queryConsumedFoods(day);
	}
	
	public PendingFoodCursor queryPendingFoods(){
		return mHelper.queryPendingFoods();
	}
	
	public Food getPendingFood(long id){
		Food food = null;
		PendingFoodCursor cursor = mHelper.queryPendingFood(id);
		cursor.moveToFirst();
		// If you got a row, get a food
		if(!cursor.isAfterLast())
			food = cursor.getFood();
		
		cursor.close();
		return food;
	}
	
	public int DeletePendingFood(String path){
		
		int result = mHelper.DeleteFromPendingFoods(path);
		Log.d(TAG, "Deleted " + result );
		return result;
	}
	
	/*public Food addOrganizedPendingFood(Food food){
		
		//Attempt to get a day
			Day day = null;
			DayCursor cursor = mHelper.queryDay(food.getDay());
			cursor.moveToFirst();
			//If you got a row, get a day
			if(!cursor.isAfterLast())
				day = cursor.getDay();
			cursor.close();
		
		//Add day to database if it's not currenty in the db
			if(day == null){
				day = new Day();
				addDay(day);
			}
		addConsumedFood(food, dayId);
		
		return food;
	}
	*/
	
	  boolean addConsumedFoodToDatabase(Food food){
	    	
		 // try{
			  
		  
			  if(food.getQuantity() == 0)
		    		food.setQuantity(1);
		    	
		   
		    	
		    	//Add food to Days table
		        //See if today's day is in the database
		       
		        Date date = new Date();
		        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		      
			    //Day currentDay = FoodManager.get(getActivity()).getDay(currentDate);
		        	int currentDayId = getDay(currentDate);
			        Log.e("TAG", "Current day is: " + currentDayId);
			        if(currentDayId == -1){//current day is not in database so we need to add it
			     	   //add current day to database
			     	   Day currentDay = new Day(currentDate);
			     	   
			     	   addDay(currentDay);
			     	   currentDayId = getDay(currentDate);
			     	   Log.e(TAG, "Current day is: " + currentDay.getId());
			     	   
			        }
			        //If the food is not in the current day 
			        try{
			        if(getConsumedFood(currentDate, food.getTitle()) == null){ 
			     	   // Add food to consumedFoods table 	
			        	addConsumedFood(food, currentDayId);
			        }
			        else{
			        	Log.e(TAG, "Food found is: " + getConsumedFood(currentDate, food.getTitle()).getTitle());
			     	   //If it is already in increase the serving size
			     	   //increment serving of food in the day
			        	incrementServing(currentDayId, food);
			        }
			        }catch(Exception e){
			        	Log.d(TAG, e.toString());
			        }
		        
		 // }catch(Exception e){
			//  Log.d(TAG, e.toString());
		 // }
		        return true;
	   }
	  
	public int decreaseConsumedFoodServing(Food food){
		 return mHelper.decreaseConsumedFoodServing(food);
	}
	
	public String getCurrentDate(){
		Date date = new Date();
	    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
	    return currentDate;
	}
}
