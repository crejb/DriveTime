package com.cjb.drivetime;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NewTrip extends Activity {

	public final static String EXTRA_MESSAGE_START_NEW_TRIP_DATA = "com.cjb.drivetime.STARTNEWTRIPDATA";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_trip);
		// Show the Up button in the action bar.
		setupActionBar();
		
		populateCarSpinner();
		populateLogbookSpinner();
	}

	private void populateLogbookSpinner() {
		//TODO: Get logbooks from DB
		String[] logbooks = new String[]{"Default logbook", "Other logbook"};
		Spinner spinner = (Spinner) findViewById(R.id.spinner_logbook);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, logbooks);
		spinner.setAdapter(adapter);
		
	}

	private void populateCarSpinner() {
		//TODO: Get cars from DB
		String[] cars = new String[]{"Default car", "Other car"};
		Spinner spinner = (Spinner) findViewById(R.id.spinner_car);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cars);
		spinner.setAdapter(adapter);
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
    	
    	String car = ((Spinner) findViewById(R.id.spinner_car)).getSelectedItem().toString();
    	String logbook = ((Spinner) findViewById(R.id.spinner_logbook)).getSelectedItem().toString();
    	Date startTime = new Date();
    	int startOdometer = Integer.parseInt(((EditText)findViewById(R.id.edit_odometer_start)).getText().toString());
    	NewTripData newTripData = new NewTripData(car, logbook, startTime, startOdometer);

    	intent.putExtra(EXTRA_MESSAGE_START_NEW_TRIP_DATA, newTripData);
    	startActivity(intent);
    }
}
