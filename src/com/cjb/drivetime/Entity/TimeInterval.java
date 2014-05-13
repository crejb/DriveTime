package com.cjb.drivetime.Entity;

import java.text.ParseException;
import java.util.Date;

import android.R.integer;

public class TimeInterval {
	private int _hours;
	private int _minutes;
	private int _seconds;
	
	public TimeInterval(int hours, int minutes, int seconds){
		_hours = hours;
		_minutes = minutes;
		_seconds = seconds;
	}
	
	public TimeInterval(long millis) {
		_hours = (int)millis / (1000 * 60 * 60);
		_minutes = (int)millis / (1000 * 60) % 60;
		_seconds = (int)millis / 1000 % 60;
	}

	public String toShortTimeString(){
		return String.format("%02d:%02d:%02d", _hours, _minutes, _seconds);
	}
	
	public static TimeInterval CalculateInterval(Date time1, Date time2){
		long elapsedMillis = time2.getTime() - time1.getTime();
		
		long hours = elapsedMillis / (1000 * 60 * 60);
		long minutes = elapsedMillis / (1000 * 60) % 60;
		long seconds = elapsedMillis / 1000 % 60;
		
		return new TimeInterval((int)hours, (int)minutes, (int)seconds);
	}

	public long getMillis() {
		return (_hours * 60 * 60 * 1000) +
			   (_minutes * 60 * 100) +
			   (_seconds * 1000);
	}

	public static TimeInterval parse(String elapsedTimeString) throws ParseException{
		try{
			String[] parts = elapsedTimeString.split(":");
			return new TimeInterval(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])); 
		
		}catch (Exception ex){
			throw new ParseException("Error parsing time string", 0);
		}
	}
}
