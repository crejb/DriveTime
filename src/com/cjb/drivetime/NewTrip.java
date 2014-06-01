package com.cjb.drivetime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.cjb.dataentrylistview.DataEntryDateListItem;
import com.cjb.dataentrylistview.DataEntryListItem;
import com.cjb.dataentrylistview.DataEntryListItemAdapter;
import com.cjb.dataentrylistview.DataEntryListListItem;
import com.cjb.dataentrylistview.DataEntryStringListItem;
import com.cjb.dataentrylistview.DataEntryTimeListItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class NewTrip extends Activity {

	public final static String EXTRA_MESSAGE_START_NEW_TRIP_DATA = "com.cjb.drivetime.STARTNEWTRIPDATA";
	private List<DataEntryListItem> _items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_trip);
		// Show the Up button in the action bar.
		setupActionBar();
		
		_items = buildListItems();

		final ListView listview = (ListView) findViewById(R.id.new_trip_data_entry_list_view);
		final ArrayAdapter<DataEntryListItem> adapter = 
				new DataEntryListItemAdapter(this, android.R.layout.simple_list_item_1, _items);
		listview.setAdapter(adapter);
	}


	private List<DataEntryListItem> buildListItems() {
		List<DataEntryListItem> items = new ArrayList<DataEntryListItem>();
		//TODO: Get values from DB
		items.add(new DataEntryListListItem("Car", "Default car", new String[]{"Default car", "Other car"}));
		items.add(new DataEntryListListItem("Logbook", "Default logbook", new String[]{"Default logbook", "Other logbook"}));
		items.add(new DataEntryStringListItem("Odometer", "15000"));
		return items;
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
		getMenuInflater().inflate(R.menu.new_trip, menu);
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

	public void buttonStartClick(View view){
    	Intent intent = new Intent(this, CurrentTrip.class);

    	String car = ((DataEntryListListItem)_items.get(0)).GetValue();
    	String logbook = ((DataEntryListListItem)_items.get(1)).GetValue();
    	int startOdometer = Integer.parseInt(((DataEntryStringListItem)_items.get(2)).GetValue());
    	
    	Date startTime = new Date();
    	NewTripData newTripData = new NewTripData(car, logbook, startTime, startOdometer);
    	
    	intent.putExtra(EXTRA_MESSAGE_START_NEW_TRIP_DATA, newTripData);
    	startActivity(intent);
    }
	
}
