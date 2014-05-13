package com.cjb.drivetime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cjb.drivetime.Entity.Refuel;

public class RefuelActivity extends Activity {

	private Date refuelDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refuel);
		
		refuelDate = new Date();
		
		populateCarSpinner();
		populateDateTimeText();
	}
	
	private void populateDateTimeText(){
		((TextView)findViewById(R.id.edit_date)).setText(SimpleDateFormat.getDateInstance().format(refuelDate));
		((TextView)findViewById(R.id.edit_time)).setText(SimpleDateFormat.getTimeInstance().format(refuelDate));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.refuel, menu);
		return true;
	}
	
	private void populateCarSpinner() {
		//TODO: Get cars from DB
		String[] cars = new String[]{"Default car", "Other car"};
		Spinner spinner = (Spinner) findViewById(R.id.spinner_car);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cars);
		spinner.setAdapter(adapter);
	}
	
	public void editTimeClick(View view){
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(refuelDate);
		OnTimeSetListener timeSetCallback = new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
				refuelDate = calendar.getTime();
				populateDateTimeText();
			}
		};
		TimePickerDialog datePicker = new TimePickerDialog(this, timeSetCallback, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
		datePicker.show();
	}
	
	public void editDateClick(View view){
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(refuelDate);
		
		OnDateSetListener dateSetCallback = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				refuelDate = calendar.getTime();
				populateDateTimeText();
			}
		};
		DatePickerDialog datePicker = new DatePickerDialog(this, dateSetCallback, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePicker.show();
	}
	
	public void buttonDoneClick(View view){
		Date time = refuelDate;
		
		/*try {
			time = SimpleDateFormat.getDateTimeInstance().parse(((EditText)findViewById(R.id.edit_time)).getText().toString());
		} catch (ParseException e) {
			ShowErrorMessage("Please enter a valid date");
			return;
		}*/	
		
		int odometerReading = Integer.parseInt(((EditText)findViewById(R.id.edit_odometer)).getText().toString());
		int cost = Integer.parseInt(((EditText)findViewById(R.id.edit_cost)).getText().toString());
		int volume = Integer.parseInt(((EditText)findViewById(R.id.edit_volume)).getText().toString());
		
		Refuel refuel = new Refuel();
		
		refuel.setDate(time);
		//refuel.setCarId((Spinner) findViewById(R.id.spinner_car)));
		refuel.setOdometerReading(odometerReading);
		refuel.setCost(cost);
		refuel.setVolume(volume);

		MainActivity.GlobalRefuelRepository.Save(refuel);
		
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
