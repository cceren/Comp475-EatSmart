package edu.harding.android.eatsmart;

import java.util.ArrayList;
import java.util.List;


public class FoodBank {
	private static String mName[] = new String[]{
		"BELGIAN WAFFLE","TURKEY SAUSAGE LINK",
		"FRIED EGG","CHEDDAR CHEESE OMELET",
		"SCRAMBLED EGGS","OLD FASHIONED OATMEAL",
		"GRITS","FRENCH TOAST",
		"BACON","HASH BROWN POTATOES",
		"DONUT BITES","CARROT RAISIN MUFFIN",
		"STEAMED YELLOW SQUASH","THANKSGIVING SANDWICH",
		"VEGETABLE CURRY WITH JASMINE RI","ROASTED GARLIC POTATOES",
		"BROCCOLI","VEGETABLE POTATO CHIPS",
		"HONEY DIJON CHICKEN WRAP","VEGAN OATMEAL COOKIE",
		"CREAM CHEESE MARBLED BROWNIE","CHOCOLATE CHIP COOKIE",
		"CHERRY JELL-O (R) PARFAIT"};
	private static String[] mCal = new String[]{
		"316.8","21.56","106.17","191.23","159.9",
		"146.25","102.91","171.3","49.35","131.42","207.85","187.63","17.21","304.78",
		"406.77","103.74","19.85","312.38","483.99",
			"177.33","259.09","124.76","121.89"};
	
	
	
	public static List<Food> creatFoodBank(){
		List<Food> foodBank = new ArrayList<Food>();
		for(int i=0; i<mName.length; i++)
		{
			Food food = new Food();
			food.setTitle(mName[i]) ;
			food.setCalories(Integer.parseInt(mCal[i]));
			foodBank.add(food);
		}
	
		return foodBank;
	}
}
