package com.cjb.drivetime.Entity;

import java.util.Date;


public class Trip {
	private int _id;
	private Date _timeStart;
	private TimeInterval _timeElapsed;
	private int _odometerStartReading;
	private int _odometerEndReading;
	
	public Trip(){
		
	}
	
	public Trip(int id, Date timeStart, TimeInterval timeElapsed, int odometerStart, int odometerEnd){
		_id = id;
		_timeStart = timeStart;
		_timeElapsed = timeElapsed;
		_odometerStartReading = odometerStart;
		_odometerEndReading = odometerEnd;
	}
	public int getId(){
		return _id;
	}
	public void setId(int id){
		_id = id;
	}
	public Date getTimeStart() {
		return _timeStart;
	}
	public void setTimeStart(Date timeStart) {
		_timeStart = timeStart;
	}
	public TimeInterval getTimeElapsed() {
		return _timeElapsed;
	}
	public void setTimeElapsed(TimeInterval timeElapsed) {
		_timeElapsed = timeElapsed;
	}
	public int getStartOdometerReading() {
		return _odometerStartReading;
	}
	public void setStartOdometerReading(int startOdometerReading) {
		_odometerStartReading = startOdometerReading;
	}
	public int getEndOdometerReading() {
		return _odometerEndReading;
	}
	public void setEndOdometerReading(int endOdometerReading) {
		_odometerEndReading = endOdometerReading;
	}

	public String toString(){
		return String.format("Trip Id:%d, Start Time:%s, Time Elapsed:%s", 
				_id, _timeStart.toString(), _timeElapsed.toShortTimeString());
	}

	public int getDistanceTravelled() {
		return _odometerEndReading - _odometerStartReading;
	}
	
}
