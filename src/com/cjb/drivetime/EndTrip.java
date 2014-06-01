package com.cjb.drivetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.cjb.dataentrylistview.DataEntryListItem;
import com.cjb.dataentrylistview.DataEntryListItemAdapter;
import com.cjb.dataentrylistview.DataEntryListListItem;
import com.cjb.dataentrylistview.DataEntryStringListItem;
import com.cjb.dataentrylistview.DataEntryTimeListItem;
import com.cjb.drivetime.Entity.TimeInterval;
import com.cjb.drivetime.Entity.Trip;

public class EndTrip extends Activity {

	private EndTripData _tripData;
	private List<DataEntryListItem> _items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_trip);
		
		Intent intent = getIntent();
		_tripData = (EndTripData) intent.getParcelableExtra(CurrentTrip.EXTRA_MESSAGE_END_TRIP_DATA);
		
		_items = buildListItems(_tripData.getStartOdometer(), _tripData.getStartTime(), _tripData.getElapsedTime());

		final ListView listview = (ListView) findViewById(R.id.end_trip_data_entry_list_view);
		final ArrayAdapter<DataEntryListItem> adapter = 
				new DataEntryListItemAdapter(this, android.R.layout.simple_list_item_1, _items);
		listview.setAdapter(adapter);
	}


	private List<DataEntryListItem> buildListItems(int odometerStart, Date timeStart, TimeInterval timeElapsed) {
		List<DataEntryListItem> items = new ArrayList<DataEntryListItem>();
		//TODO: Get values from DB
		items.add(new DataEntryListListItem("Car", "Default car", new String[]{"Default car", "Other car"}));
		items.add(new DataEntryListListItem("Logbook", "Default logbook", new String[]{"Default logbook", "Other logbook"}));
		items.add(new DataEntryStringListItem("Odometer Start", Integer.toString(odometerStart)));
		items.add(new DataEntryStringListItem("Odometer End", Integer.toString(odometerStart + 10)));
		items.add(new DataEntryTimeListItem("Time Started", timeStart));
		items.add(new DataEntryStringListItem("Time Elapsed", timeElapsed.toShortTimeString()));
		return items;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end_trip, menu);
		return true;
	}

	public void buttonDoneClick(View view){
		Trip trip = new Trip();

		String car = ((DataEntryListListItem)_items.get(0)).GetValue();
    	String logbook = ((DataEntryListListItem)_items.get(1)).GetValue();
    	int startOdometerReading = Integer.parseInt(((DataEntryStringListItem)_items.get(2)).GetValue());
    	int endOdometerReading = Integer.parseInt(((DataEntryStringListItem)_items.get(3)).GetValue());
		Date startTime = ((DataEntryTimeListItem)_items.get(4)).GetValue();
		TimeInterval elapsed;
		try {
			String elapsedTimeString = ((DataEntryStringListItem)_items.get(5)).GetValue();
			elapsed = TimeInterval.parse(elapsedTimeString);
		} catch (ParseException e) {
			ShowErrorMessage("Please enter a valid elapsed time");
			return;
		}
		
		trip.setTimeStart(startTime);
		trip.setTimeElapsed(elapsed);
		trip.setStartOdometerReading(startOdometerReading);
		trip.setEndOdometerReading(endOdometerReading);

		MainActivity.GlobalTripRepository.Save(trip);
		
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		startActivity(intent);
	}
	
	private void ShowErrorMessage(String message){
		new AlertDialog.Builder(this)
		.setTitle("Error")
		.setMessage(message)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.ok, null)
		.show();
	}
}
