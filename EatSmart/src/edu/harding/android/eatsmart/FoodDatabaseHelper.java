package edu.harding.android.eatsmart;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodDatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "FoodDatabaseHelper";
	private static final String DB_NAME = "foods.sqlite";
	
	private static final int Version  = 1;
	
	private static final String TABLE_FOOD = "food";
	private static final String TABLE_DAYS = "days";
	private static final String TABLE_CONSUMED_FOOD = "consumedFood";
	private static final String TABLE_PENDING_FOOD = "pendingFood";
	
	
	private static final String COLUMN_DATE = "date";
	private static final String COLUMN_FOOD_DAY_ID = "day_id";
	private static final String COLUMN_FOOD_CALORIES = "calories";
	private static final String COLUMN_FOOD_NAME = "name";
	private static final String COLUMN_FOOD_ID = "_id";
	private static final String COLUMN_FOOD_SERVINGS = "servings";
	private static final String COLUMN_PENDING_FOOD_PHOTO_FILENAME = "photoFilename";
	
	public FoodDatabaseHelper(Context context){
		super(context, DB_NAME, null, Version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create food table
        String CREATE_FOOD_TABLE = "CREATE TABLE food ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "name TEXT, "+
                "calories INTEGER, " +
                "servingSize TEXT )";
        
        String CREATE_DAYS_TABLE = "CREATE TABLE days ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "date TEXT, "+
                "calories INTEGER )";
                
               
        
        String CREATE_CONSUMED_FOOD_TABLE = "CREATE TABLE consumedFood ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "name TEXT, " +
                "servings INTEGER, " +
                "calories INTEGER, " +
                "time TEXT, " +
                "day TEXT, " +
                "servingSize TEXT," +
                "day_id INTEGER references days(_id))";
        
        String CREATE_PENDING_FOOD_TABLE = "CREATE TABLE pendingFood ( " +
        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "date TEXT, " +
        "time TEXT, " +
        "photoFilename TEXT)";
        
        final String[] foods = {"CRUNCHY CORN DOGS", "HOT DOG", "FRENCH FRIES", "STEAMED GREENPEAS", "STEAKHOUSE POTATOES",
        		"VEGETABLE BEAN CHILI", "SWEET & SOUR PORK", "BASMATI RICE WITH PEAS", "BRAISED APPLE PORK CHOP", "CARROTS"};
        
        final Integer[] calories = {273, 289, 295, 61, 120, 109, 147, 177, 181, 26};
        
        final String[] servingSize = {"1 each", "1 each", "3.25 ounces", "1/2 cup", "1/2 cup", "8 fl oz", "1/2 cup", "1/2 cup",
        	"1 each ", "1/2 cup"};
        
        
        
		try{
	        // create food table
	        db.execSQL(CREATE_FOOD_TABLE);
	        // create days table
	        db.execSQL(CREATE_DAYS_TABLE);
	        // create consumedFood table
	        db.execSQL(CREATE_CONSUMED_FOOD_TABLE);
			// create pendingFood table
	        db.execSQL(CREATE_PENDING_FOOD_TABLE);
	        
	        for(int i = 0; i < foods.length; i++){
				Food f = new Food();
				f.setTitle(foods[i]);
				f.setCalories(calories[i]);
				f.setServingSize(servingSize[i]);
				
				ContentValues cv = new ContentValues();
				 Log.d(TAG, f.getTitle());
				cv.put(COLUMN_FOOD_NAME, f.getTitle());
				cv.put(COLUMN_FOOD_CALORIES, f.getCalories());
				cv.put("servingSize", f.getServingSize());
				
				db.insert(TABLE_FOOD, null, cv);
			}
	        Log.d(TAG, "Database tables created");
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		//Implement schema changes and data massage here when upgrading
	}

	public long insertFood(Food food){
		try{
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_FOOD_NAME, food.getTitle());
			cv.put(COLUMN_FOOD_CALORIES, food.getCalories());
			Log.d(TAG, "Adding item to database");
			return getWritableDatabase().insert(TABLE_FOOD, null, cv);
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return 0;
	}
	
	
	
	public long insertConsumedFood(Food food, long dayId, int servingSize, String day){
		try{
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_FOOD_NAME, food.getTitle());
			cv.put(COLUMN_FOOD_CALORIES, food.getCalories());
			cv.put(COLUMN_FOOD_DAY_ID, dayId);
			cv.put(COLUMN_FOOD_SERVINGS, servingSize);
			cv.put("day", day);
			Log.d(TAG, "Adding food to consumedFood table");
			
			return getWritableDatabase().insert(TABLE_CONSUMED_FOOD, null, cv);
			
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return 0;
	}
	
	public long updateConsumedFood(long dayId, int servingSize, String foodName){
		int rowsAffected = 0;
		try{
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_FOOD_SERVINGS, servingSize);
			Log.d(TAG, "Updating food from consumedFood table");
			rowsAffected = getWritableDatabase().update(TABLE_CONSUMED_FOOD, cv, 
					COLUMN_FOOD_DAY_ID + " = ? AND " + COLUMN_FOOD_NAME + " = ?", new String []{String.valueOf(dayId), foodName});
			 return rowsAffected;
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return rowsAffected;
	}
	public long updateDay(Day day){
		int rowsAffected = 0;
		try{
			ContentValues cv = new ContentValues();
			
			cv.put("calories", day.getTotalCalories());
			rowsAffected = getWritableDatabase().update("days", cv, 
					"date = ? " , new String []{day.getDate()});
			 return rowsAffected;
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return rowsAffected;
	}
	public long updateConsumedFood(Food food){
		int rowsAffected = 0;
		try{
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_FOOD_SERVINGS, food.getQuantity());
			Log.d(TAG, "Updating food from consumedFood table");
			rowsAffected = getWritableDatabase().update(TABLE_CONSUMED_FOOD, cv, 
					"day = ? AND " + COLUMN_FOOD_NAME + " = ?", new String []{food.getDay(), food.getTitle()});
			 return rowsAffected;
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return rowsAffected;
	}
	
	public long insertPendingFood(Food food){
		try{
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_DATE, food.getPhotoDate());
			cv.put(COLUMN_PENDING_FOOD_PHOTO_FILENAME, food.getPhotoFilename());
			cv.put("time", food.getTime());
			Log.d(TAG, "Adding: pending food to pendingFood table");
			return getWritableDatabase().insert(TABLE_PENDING_FOOD, null, cv);
		}
		catch(Exception e){
			Log.d(TAG,"insertPendingFood() " + e.toString());
		}
		return 0;
	}
	public long insertDay(Day day){
		
		try{
			ContentValues cv = new ContentValues();
			
			cv.put(COLUMN_DATE, day.getDate().toString());
			cv.put(COLUMN_FOOD_CALORIES, day.getTotalCalories());
			Log.d(TAG, "Adding: " + day.getDate().toString() +"  to daysTable");
			return getWritableDatabase().insert(TABLE_DAYS, null, cv);
		}
		catch(Exception e){
			Log.d(TAG,"insertDay() " + e.toString());
		}
		return 0;
	}
	
	public FoodCursor queryFoods(){
		//Equivalent to "select * from food order by name asc"
		try{
			Cursor wrapped = getReadableDatabase().query(TABLE_FOOD,
					null, null, null, null, null, null);
			Log.d(TAG, "queryFoods");
			return new FoodCursor(wrapped);
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return null;
	}
	
	/**
	 * A convenience class to wrap a cursor that returns rows from the "foods" table.
	 * The{@link getFood()} method will give you a Food instance representing
	 * the current row.
	 */
	
	public static class FoodCursor extends CursorWrapper{
		public FoodCursor(Cursor c){
			super(c);
		}
		
	/**
	 * Return a food object configured for the current row,
	 * or null if the current row is invalid.
	 */
		public Food getFood(){
			if(isBeforeFirst() || isAfterLast())
				return null;
			
			Food food = new Food();
			String foodName = getString(getColumnIndex(COLUMN_FOOD_NAME));
			int foodCalories = getInt(getColumnIndex(COLUMN_FOOD_CALORIES));
			int foodId = getInt(getColumnIndex(COLUMN_FOOD_ID));
			String servingSize = getString(getColumnIndex("servingSize"));
			
			food.setCalories(foodCalories);
			food.setTitle(foodName);
			food.setFoodId(foodId);
			food.setServingSize(servingSize);
			
			return food;
			
		}
	}
	
	public FoodCursor queryFood(long id){
		Cursor wrapped = getReadableDatabase().query(TABLE_FOOD,
				null, //All columns
				COLUMN_FOOD_ID + " = ?", //Look for a food ID 
				new String[]{String.valueOf(id)}, // with this value
				null, // group by
				null, //order by
				null, //having
				"1"); //limit 1 row
		
		return new FoodCursor(wrapped);
	}
	
	public ConsumedFoodCursor queryConsumedFood(String day, String foodName){
		Cursor wrapped = getReadableDatabase().query(TABLE_CONSUMED_FOOD,
				null, //All columns
				"day = ? AND " + COLUMN_FOOD_NAME + " = ?", //limit to a particular day 
				new String []{day, foodName}, // with this value
				null, // group by
				null, //having
				null, //order by
				"1"); //limit 1 row
		
		return new ConsumedFoodCursor(wrapped);
		
		
	}

	public int queryDayTotalCalories(long dayId){
		int totalCalories = 0;
		Cursor wrapped = getReadableDatabase().query(TABLE_DAYS,
				null, // All columns
				COLUMN_FOOD_ID + " = ? ", //limit to a particular day 
				new String []{String.valueOf(dayId)}, // with this value
				null,
				null, //having
				null, //order by
				"1"); //limit 1 row
		
		if (wrapped.moveToFirst()) // data?
			totalCalories = wrapped.getInt(wrapped.getColumnIndex(COLUMN_FOOD_CALORIES)); 
		
		return totalCalories;
		
	}
	
	public int queryDayTotalCalories(String day){
		int totalCalories = 0;
		Cursor wrapped = getReadableDatabase().query(TABLE_DAYS,
				null, // All columns
				"date = ? ", //limit to a particular day 
				new String []{day}, // with this value
				null,
				null, //having
				null, //order by
				"1"); //limit 1 row
		
		if (wrapped.moveToFirst()) // data?
			totalCalories = wrapped.getInt(wrapped.getColumnIndex(COLUMN_FOOD_CALORIES)); 
		
		return totalCalories;
		
	}
	
	public long updateDayTotalCalories(long dayId, int totalCalories){
		try{
			ContentValues cv = new ContentValues();
			cv.put(COLUMN_FOOD_CALORIES, totalCalories);
			return getWritableDatabase().update(TABLE_DAYS, cv, 
					"_id" + " = ?", new String []{String.valueOf(dayId)});	
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return 0;
	}
	public long updateDayTotalCalories(String date, int totalCalories){
		try{
			ContentValues cv = new ContentValues();
			cv.put(COLUMN_FOOD_CALORIES, totalCalories);
			return getWritableDatabase().update(TABLE_DAYS, cv, 
					"date = ?", new String []{date});	
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		return 0;
	}
	
	public int DeleteFromPendingFoods(String path){

		
		return getReadableDatabase().delete(TABLE_PENDING_FOOD, 
				COLUMN_PENDING_FOOD_PHOTO_FILENAME + " = ? ", new String []{path}); //limit 1 row
		
	}
	
	/***
	 * Des: decrease the serving of a consumed food, otherwise
	 * it deletes it from the database
	 * @param food
	 * @return
	 */
	public int decreaseConsumedFoodServing(Food food){
		int servings = food.getQuantity();

		if( servings > 1){
			food.setQuantity(servings - 1);
			updateConsumedFood(food);
		}else{
			deleteConsumedFood(food);
		}
		try{
			Day day = null;
			DayCursor cursor = queryDay(food.getDay());
			cursor.moveToFirst();
			//If you got a row, get a day
			if(!cursor.isAfterLast())
				day = cursor.getDay();
			cursor.close();
			
			
			int totalCalories = day.getTotalCalories() - food.getCalories();
			day.setTotalCalories(totalCalories);
			
			updateDay(day);
		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
			
		
		return 0;
	}
	
	
	//Des: Deletes a row from the consumedFood table
	public int deleteConsumedFood(Food food){

		
		
		return getReadableDatabase().delete(TABLE_CONSUMED_FOOD, 
				"name = ? AND day = ?", new String []{food.getTitle(), food.getDay()}); //limit 1 row
		
	}
	
	public int queryFoodServing(long dayId, String foodName){
		int servingSize = 0;
		Cursor wrapped = getReadableDatabase().query(TABLE_CONSUMED_FOOD,
				null, // All columns
				COLUMN_FOOD_DAY_ID + " = ? AND " + COLUMN_FOOD_NAME + " = ?", //limit to a particular day 
				new String []{String.valueOf(dayId), foodName}, // with this value
				null,
				null, //having
				null, //order by
				"1"); //limit 1 row

		if (wrapped.moveToFirst()) // data?
			servingSize = wrapped.getInt(wrapped.getColumnIndex(COLUMN_FOOD_SERVINGS)); 

		return servingSize;

	}
	
	public DayCursor queryDay(String date){
		Cursor wrapped = getReadableDatabase().query(TABLE_DAYS, 
				null, // All columns
				COLUMN_DATE + " = ?", //limit to given day
				new String[]{date},
				null, // group by
				null, // having
				null,
				"1"); // limit 1
		return new DayCursor(wrapped);
				
	}
	
	public static class DayCursor extends CursorWrapper{
		public DayCursor(Cursor c){
			super(c);
		}
		
		public Day getDay(){
			if(isBeforeFirst() || isAfterLast())
				return null;
			
			Day day = new Day();
			String dayDate = getString(getColumnIndex(COLUMN_DATE));
			int totalCalories = getInt(getColumnIndex(COLUMN_FOOD_CALORIES));
			day.setDate(dayDate);
			day.setTotalCalories(totalCalories);
			return day;
		}
	}
	
	public ConsumedFoodCursor queryConsumedFoods(String day){
		//Equivalent to "select * from where day
		try{
			Cursor wrapped = getReadableDatabase().query(TABLE_CONSUMED_FOOD,
					null, 
					"day = ?", 
					new String[]{day}, 
					null, 
					null, 
					null);
			Log.d(TAG, "queryConsumedFoods according to current day");
			return new ConsumedFoodCursor(wrapped);

		}
		catch(Exception e){
			Log.d(TAG, e.toString());
		}
		
	
		return null;
	}
	

	public PendingFoodCursor queryPendingFoods(){
		try{
			Cursor wrapped = getReadableDatabase().query(TABLE_PENDING_FOOD,
					null, null, null, null, null, null);
			Log.d(TAG, "queryingPendingFoods()");
			return new PendingFoodCursor(wrapped);

		}
		catch(Exception e){
			Log.d(TAG, "queryPendingFoods() " + e.toString());
		}
		return null;
		
	}
	
	public PendingFoodCursor queryPendingFood(long id){
		try{
			Cursor wrapped = getReadableDatabase().query(TABLE_PENDING_FOOD, 
					null, // All columns
					COLUMN_FOOD_ID + " = ?", //limit to given day
					new String[]{String.valueOf(id)},
					null, // group by
					null, // having
					null,
					"1"); // limit 1
			Log.d(TAG, "queryingPendingFood()");
			return new PendingFoodCursor(wrapped);

		}
		catch(Exception e){
			Log.d(TAG, "queryPendingFoods() " + e.toString());
		}
		return null;
		
	}
	
	/**
	 * A convenience class to wrap a cursor that returns rows from the "consumedFoods" table.
	 * The{@link getFood()} method will give you a Food instance representing
	 * the current row.
	 */
	public static class ConsumedFoodCursor extends CursorWrapper{
		public ConsumedFoodCursor(Cursor c){
			super(c);
		}
		
	/**
	 * Return a food object configured for the current row,
	 * or null if the current row is invalid.
	 */
		public Food getFood(){
			if(isBeforeFirst() || isAfterLast())
				return null;
			Food food = new Food();
			String foodName = getString(getColumnIndex(COLUMN_FOOD_NAME));
			int foodCalories = getInt(getColumnIndex(COLUMN_FOOD_CALORIES));
			int foodQuantity = getInt(getColumnIndex(COLUMN_FOOD_SERVINGS));
			String servingSize = getString(getColumnIndex("servingSize"));
			String day = getString(getColumnIndex("day"));
			
			food.setServingSize(servingSize);
			food.setDay(day);
			food.setCalories(foodCalories);
			food.setTitle(foodName);
			food.setQuantity(foodQuantity);
			
			
			return food;
			
		}
	}
	
	public static class PendingFoodCursor extends CursorWrapper{
		public PendingFoodCursor(Cursor c){
			super(c);
		}
		
	/**
	 * Return a food object configured for the current row,
	 * or null if the current row is invalid.
	 */
		public Food getFood(){
			if(isBeforeFirst() || isAfterLast())
				return null;
			Food food = new Food();
			String photoFilename = getString(getColumnIndex(COLUMN_PENDING_FOOD_PHOTO_FILENAME));
			String photoDate = getString(getColumnIndex(COLUMN_DATE));
			
			food.setTime(getString(getColumnIndex("time")));
			food.setPhotoFilename(photoFilename);
			food.setPhotoDate(photoDate);
			Photo photo = new Photo(photoFilename);
			food.setPhoto(photo);

			return food;
			
		}
	}
	
	
	
	
}
