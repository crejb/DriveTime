package com.cjb.drivetime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.cjb.drivetime.Repositories.IRefuelRepository;
import com.cjb.drivetime.Repositories.ITripRepository;
import com.cjb.drivetime.database.DriveTimeDbHelper;
import com.cjb.drivetime.database.RefuelRepository;
import com.cjb.drivetime.database.TripRepository;

public class MainActivity extends Activity {
	public static ITripRepository GlobalTripRepository;
	public static IRefuelRepository GlobalRefuelRepository;
	
	public MainActivity(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(GlobalTripRepository == null){
			Context context = this.getApplicationContext();
			DriveTimeDbHelper dbHelper = null;
			try{
				dbHelper = new DriveTimeDbHelper(context);			
			}catch(Exception ex){
				System.out.println(ex.toString());
			}
			
			GlobalTripRepository = new TripRepository(dbHelper);
			GlobalRefuelRepository = new RefuelRepository(dbHelper);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void buttonStartClick(View view){
    	Intent intent = new Intent(this, NewTrip.class);
    	startActivity(intent);
    }
	
	public void buttonViewLogsClick(View view){
		Intent intent = new Intent(this, ViewLogs.class);
		startActivity(intent);
    }
	
	public void buttonViewStatisticsClick(View view){
		Intent intent = new Intent(this, StatisticsActivity.class);
		//Intent intent = new Intent(this, TestActivity.class);
    	startActivity(intent);
    }
	
	public void buttonRefuelClick(View view){
    	Intent intent = new Intent(this, RefuelActivity.class);
    	startActivity(intent);
    }
	
	public void buttonCancelClick(View view){
		// Don't allow back button on main screen
	}
}
