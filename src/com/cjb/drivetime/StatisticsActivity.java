package com.cjb.drivetime;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.cjb.drivetime.Entity.Refuel;
import com.cjb.drivetime.Entity.Trip;

public class StatisticsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		// Show the Up button in the action bar.
		setupActionBar();
		
		List<Trip> allTrips = MainActivity.GlobalTripRepository.GetAll();
		List<Refuel> allRefuels = MainActivity.GlobalRefuelRepository.GetAll();
		
				
		double averageRefuelAmount = calculateAverageRefuelAmount(allRefuels);
		((StatisticGroupControl)findViewById(R.id.statistic1)).setItemValue(String.format("%.2fL",averageRefuelAmount));
		((StatisticGroupControl)findViewById(R.id.statistic1)).setItemDescription("per refuel");
		
		double averageRefuelCost = calculateAverageRefuelCost(allRefuels);
		((StatisticGroupControl)findViewById(R.id.statistic2)).setItemValue(String.format("$%.2f",averageRefuelCost));
		((StatisticGroupControl)findViewById(R.id.statistic2)).setItemDescription("per refuel");
		
		double averageTripDistance = calculateAverageTripDistance(allTrips);
		((StatisticGroupControl)findViewById(R.id.statistic3)).setItemValue(String.format("%.2fkm",averageTripDistance));
		((StatisticGroupControl)findViewById(R.id.statistic3)).setItemDescription("per trip");
	}
	
	private double calculateAverageTripDistance(List<Trip> allTrips) {
		if(allTrips.size() == 0){
			return 0;
		}
		
		int totalDistance = 0;
		for(Trip t : allTrips){
			totalDistance += t.getDistanceTravelled();
		}
		
		return (double)totalDistance / allTrips.size();
	}

	private double calculateAverageRefuelAmount(List<Refuel> allRefuels){
		if(allRefuels.size() == 0){
			return 0;
		}
		
		int totalRefuel = 0;
		for(Refuel r : allRefuels){
			totalRefuel += r.getVolume();
		}
		
		return (double)totalRefuel / allRefuels.size();
	}
	
	private double calculateAverageRefuelCost(List<Refuel> allRefuels){
		if(allRefuels.size() == 0){
			return 0;
		}
		
		int totalCost = 0;
		for(Refuel r : allRefuels){
			totalCost += r.getCost();
		}
		
		return (double)totalCost / allRefuels.size();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
