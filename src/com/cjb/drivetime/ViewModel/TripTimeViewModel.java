package com.cjb.drivetime.ViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cjb.drivetime.Entity.TimeInterval;

public class TripTimeViewModel {
	private List<ITripTimerListener> _tripTimeListeners;
	private Date _startTime;
	private TimeInterval _elapsedTime;
	
	public TripTimeViewModel(Date startTime){
		_tripTimeListeners = new ArrayList<ITripTimerListener>();
		_startTime = startTime;
		startTimer();
	}
	
	public Date getStartTime(){
		return _startTime;
	}
	
	public TimeInterval getElapsedTime(){
		return _elapsedTime;
	}
	
	public void addTripTimeListener(ITripTimerListener listener){
		_tripTimeListeners.add(listener);
	}
	
	private void startTimer() {
		Timer myTimer = new Timer();
	      myTimer.schedule(new TimerTask() {
	         @Override
	         public void run() {
	        	 _elapsedTime =  TimeInterval.CalculateInterval(_startTime, new Date());
				  onElapsedTimeChanged();
	        	 }
	      }, 0, 1000);
	}

	private void onElapsedTimeChanged(){
		for(ITripTimerListener listener : _tripTimeListeners){
			listener.tripTimeChanged(_elapsedTime);
		}
	}

}
