package edu.harding.android.eatsmart;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CalorieCounterActivity extends FragmentActivity {
/*Added another comment*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_count);
		
		
		/*ListView lv = (ListView)findViewById(R.id.listFood);
		final List<Food> listFood=FoodBank.creatFoodBank();
		FoodAdapter adapter=new FoodAdapter(this, listFood);
		lv.setAdapter(adapter);	
		Button submit = (Button)findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                CalorieCounterFragment calorieCounterFragment = new CalorieCounterFragment();
                ft.replace(R.id.fragmentContainer, calorieCounterFragment).commit();
			}
		});
		*/
		

	}
}
