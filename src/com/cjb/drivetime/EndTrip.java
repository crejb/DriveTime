package com.cjb.drivetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cjb.drivetime.Entity.TimeInterval;
import com.cjb.drivetime.Entity.Trip;

public class EndTrip extends Activity {

	EndTripData _tripData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_trip);
		
		Intent intent = getIntent();
		_tripData = (EndTripData) intent.getParcelableExtra(CurrentTrip.EXTRA_MESSAGE_END_TRIP_DATA);
		
		populateCarSpinner(_tripData.getCar());
		populateLogbookSpinner(_tripData.getLogbook());
		((TextView)findViewById(R.id.edit_time_start)).setText(SimpleDateFormat.getTimeInstance().format(_tripData.getStartTime()));
		((EditText)findViewById(R.id.edit_time_elapsed)).setText(String.valueOf(_tripData.getElapsedTime().toShortTimeString()));
		((EditText)findViewById(R.id.edit_odometer_start)).setText(String.valueOf(_tripData.getStartOdometer()));
		
	}

	private void populateCarSpinner(String defaultValue) {
		//TODO: Get cars from DB
		String[] cars = new String[]{"Default car", "Other car"};
		Spinner spinner = (Spinner) findViewById(R.id.spinner_car);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cars);
		spinner.setAdapter(adapter);
		spinner.setSelection(adapter.getPosition(defaultValue));
	}
	
	private void populateLogbookSpinner(String defaultValue) {
		//TODO: Get logbooks from DB
		String[] logbooks = new String[]{"Default logbook", "Other logbook"};
		Spinner spinner = (Spinner) findViewById(R.id.spinner_logbook);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, logbooks);
		spinner.setAdapter(adapter);
		spinner.setSelection(adapter.getPosition(defaultValue));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end_trip, menu);
		return true;
	}

	public void buttonDoneClick(View view){
		Trip trip = new Trip();

		Date startTime = _tripData.getStartTime();
		
		TimeInterval elapsed;
		try {
			String elapsedTimeString = ((EditText)findViewById(R.id.edit_time_elapsed)).getText().toString();
			elapsed = TimeInterval.parse(elapsedTimeString);
		} catch (ParseException e) {
			ShowErrorMessage("Please enter a valid elapsed time");
			return;
		}
		
		int startOdometerReading = Integer.parseInt(((EditText)findViewById(R.id.edit_odometer_start)).getText().toString());
		int endOdometerReading = Integer.parseInt(((EditText)findViewById(R.id.edit_odometer_end)).getText().toString());
		
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
