package com.cjb.drivetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjb.drivetime.Entity.Refuel;
import com.cjb.drivetime.Entity.Trip;
import com.cjb.drivetime.ViewModel.ViewLogListItem;
import com.cjb.drivetime.ViewModel.ViewLogListItemAdapter;

public class ViewLogs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_logs);
		// Show the Up button in the action bar.
		setupActionBar();
		
		List<ViewLogListItem> items = buildListItems();

    	final ListView listview = (ListView) findViewById(R.id.listview);
    	final ArrayAdapter<ViewLogListItem> adapter = 
    			new ViewLogListItemAdapter(this, android.R.layout.simple_list_item_1, items);
    	listview.setAdapter(adapter);
	}

	private List<ViewLogListItem> buildListItems() {
		HashSet<Date> uniqueDates = new HashSet<Date>();
		List<ViewLogListItem> items = new ArrayList<ViewLogListItem>();
		
		List<Trip> trips = MainActivity.GlobalTripRepository.GetAll();
		for (Trip trip : trips){
			// Add a date header for each unique date
			Date tripDate = getDateWithoutTime(trip.getTimeStart());
			if(uniqueDates.add(tripDate)){
				items.add(new ViewLogListItem(tripDate));
			}
			
			// Add the trips
			items.add(new ViewLogListItem(trip));
		}
		
		List<Refuel> refuels = MainActivity.GlobalRefuelRepository.GetAll();
		for (Refuel refuel : refuels){
			// Add a date header for each unique date
			Date refuelDate = getDateWithoutTime(refuel.getDate());
			if(uniqueDates.add(refuelDate)){
				items.add(new ViewLogListItem(refuelDate));
			}
			
			// Add the refuels
			items.add(new ViewLogListItem(refuel));
		}

		// Display sorted from newest to oldest
		Collections.sort(items);
		return items;
	}
	
	private Date getDateWithoutTime(Date date){
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    Date calDate = cal.getTime();
	    
	    Date stringDate = null;
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
	    try {
			stringDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			
		}
	    
	    return stringDate;
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
		getMenuInflater().inflate(R.menu.view_logs, menu);
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
