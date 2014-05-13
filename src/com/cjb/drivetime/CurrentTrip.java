package com.cjb.drivetime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.cjb.drivetime.Entity.TimeInterval;
import com.cjb.drivetime.Entity.Trip;
import com.cjb.drivetime.ViewModel.ITripTimerListener;
import com.cjb.drivetime.ViewModel.TripTimeViewModel;

public class CurrentTrip extends Activity implements ITripTimerListener {
	
	public final static String EXTRA_MESSAGE_END_TRIP_DATA = "com.cjb.drivetime.endtripdata";
	
	private TripTimeViewModel _viewModel;
	private NewTripData _newTripData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current_trip);
		
		Intent intent = getIntent();
		_newTripData = (NewTripData) intent.getParcelableExtra(NewTrip.EXTRA_MESSAGE_START_NEW_TRIP_DATA);
		_viewModel = new TripTimeViewModel(_newTripData.getStartTime());
		_viewModel.addTripTimeListener(this);
		
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String timeString =  df.format(_newTripData.getStartTime()); 
		TextView startTimeText = (TextView)findViewById(R.id.text_start_time);
		startTimeText.setText(timeString);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.current_trip, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		PromptForConfirmationBeforeCancelling();
	}
	
	public void buttonFinishClick(View view){
		EndTripData endTripData = 
				new EndTripData(_newTripData.getCar(), _newTripData.getLogbook(), _newTripData.getStartTime(), _viewModel.getElapsedTime(), _newTripData.getStartOdometer());
		
		Intent intent = new Intent(this, EndTrip.class);
		intent.putExtra(EXTRA_MESSAGE_END_TRIP_DATA, endTripData);
    	startActivity(intent);
	}
	
	public void buttonCancelClick(View view){
		PromptForConfirmationBeforeCancelling();
	}
	
	private void PromptForConfirmationBeforeCancelling(){
		new AlertDialog.Builder(this)
		.setTitle("Cancel current trip?")
		.setMessage("Are you sure you wish to cancel this trip?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    	Intent intent = new Intent(getBaseContext(), MainActivity.class);
				startActivity(intent);
		    }})
		 .setNegativeButton(android.R.string.no, null)
		 .show();
	}

	@Override
	public void tripTimeChanged(final TimeInterval elapsed) {
		this.runOnUiThread(new Runnable() {
			public void run() {
				String timeString = elapsed.toShortTimeString(); 
				TextView elapsedTimeText = ((TextView)findViewById(R.id.text_elapsed_time));
				elapsedTimeText.setText(timeString);
			 }});
	}
}
