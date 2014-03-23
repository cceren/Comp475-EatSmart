package edu.harding.android.eatsmart;

import java.sql.Date;

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
	private static final String COLUMN_DATE = "date";
	
	private static final String COLUMN_FOOD_CALORIES = "calories";
	private static final String COLUMN_FOOD_NAME = "name";
	private static final String COLUMN_FOOD_ID = "_id";
	
	public FoodDatabaseHelper(Context context){
		super(context, DB_NAME, null, Version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create food table
        String CREATE_FOOD_TABLE = "CREATE TABLE food ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "name TEXT, "+
                "calories INTEGER )";
        
        String CREATE_DAYS_TABLE = "CREATE TABLE days ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "date TEXT )";
                
               
        
        String CREATE_CONSUMED_FOOD_TABLE = "CREATE TABLE consumedFood ( " +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "name TEXT, " +
                "servings INTEGER, " +
                "calories INTEGER, " +
                "day_id INTEGER references days(_id))";
		try{
	        // create food table
	        db.execSQL(CREATE_FOOD_TABLE);
	        // create days table
	        db.execSQL(CREATE_DAYS_TABLE);
	        // create consumedFood table
	        db.execSQL(CREATE_CONSUMED_FOOD_TABLE);
			
	        Log.d(TAG, "Databases created");
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
			
			food.setCalories(foodCalories);
			food.setTitle(foodName);
			
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
	
	
	public DayCursor queryDay(String date){
		Cursor wrapped = getReadableDatabase().query(TABLE_DAYS, 
				null, // All columns
				COLUMN_DATE + " = ?", //limit to given day
				new String[]{String.valueOf(date)},
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
		
		public Day getDay(String date){
			if(isBeforeFirst() || isAfterLast())
				return null;
			
			Day day = new Day();
			Date dayDate = Date.valueOf(getString(getColumnIndex(COLUMN_DATE)));
			day.setDate(dayDate);
			return day;
		}
	}
	
	
}
