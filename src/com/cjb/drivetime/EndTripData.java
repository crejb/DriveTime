package com.cjb.drivetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

import com.cjb.drivetime.Entity.TimeInterval;

public class EndTripData implements Parcelable {
	private String _car;
	private String _logbook;
	private Date _startTime;
	private TimeInterval _elapsedTime;
	private int _startOdometer;
	private SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	
	public EndTripData(String car, String logbook, Date startTime, TimeInterval elapsedTime, int startOdometer){
		_car = car;
		_logbook = logbook;
		_startTime = startTime;
		_elapsedTime = elapsedTime;
		_startOdometer = startOdometer;
	}
	
	private EndTripData(Parcel in) {
	   _car = in.readString();
	   _logbook = in.readString();
	   try {
		   _startTime = _dateFormat.parse(in.readString());
	   } catch (ParseException e) {
		   e.printStackTrace();
		   _startTime = new Date();
	   }
	   _elapsedTime = new TimeInterval(in.readLong());
	   _startOdometer = in.readInt();
	}

	public void writeToParcel(Parcel out, int flags) {
	   out.writeString(_car);
	   out.writeString(_logbook);
	   out.writeString(_dateFormat.format(_startTime));
	   out.writeLong(_elapsedTime.getMillis());
	   out.writeInt(_startOdometer);
	}
	
	public int describeContents() {
		   return 0;
		}

	public static final Parcelable.Creator<EndTripData> CREATOR = new Parcelable.Creator<EndTripData>() {
	   public EndTripData createFromParcel(Parcel in) {
	       return new EndTripData(in);
	   }
	
	   public EndTripData[] newArray(int size) {
	       return new EndTripData[size];
	   }
	};

	public String getCar(){
		return _car;
	}
	public String getLogbook(){
		return _logbook;
	}
	public Date getStartTime() {
		return _startTime;
	}
	public TimeInterval getElapsedTime() {
		return _elapsedTime;
	}
	public int getStartOdometer() {
		return _startOdometer;
	}
}
