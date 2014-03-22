package edu.harding.android.eatsmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FoodDatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "foods.sqlite";
	private static final int Version  = 1;
	
	private static final String TABLE_FOOD = "food";
	private static final String COLUMN_FOOD_CALORIES = "calories";
	private static final String COLUMN_FOOD_NAME = "name";
	
	public FoodDatabaseHelper(Context context){
		super(context, DB_NAME, null, Version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create the "food" table
		try{
		db.execSQL("create table " + TABLE_FOOD +" (" +
				"_id integer primary key autoincrement, COLUMN_FOOD_NAME varchar(100)," +
				" COLUMN_FOOD_CALORIES integer)");
		
		}
		catch(Exception e){
			Log.d("DB", e.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		//Implement schema changes and data massage here when upgrading
	}

	public long insertFood(Food food){
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_FOOD_NAME, food.getTitle());
		cv.put(COLUMN_FOOD_CALORIES, food.getCalories());
		return getWritableDatabase().insert(TABLE_FOOD, null, cv);
	}
	
	public FoodCursor queryFoods(){
		//Equivalent to "select * from food order by name asc"
		try{
			Cursor wrapped = getReadableDatabase().query(TABLE_FOOD,
					null, null, null, null, null, null);
			return new FoodCursor(wrapped);

		}
		catch(Exception e){
			Log.d("DB", e.toString());
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
}
