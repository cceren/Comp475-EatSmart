package edu.harding.android.eatsmart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
		db.execSQL("create table food(" +
				"_id integer primary key autoincrement, COLUMN_FOOD_NAME varchar(100)," +
				"COLUMN_FOOD_CALORIES integer)");

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
		Cursor wrapped = getReadableDatabase().query(TABLE_FOOD,
				null, null, null, null, null, COLUMN_FOOD_NAME + " asc");
		return new FoodCursor(wrapped);
	}
	
	/**
	 * A convenience
	 */
	
	public static class FoodCursor extends CursorWrapper{
		public FoodCursor(Cursor c){
			super(c);
		}
	}
}
