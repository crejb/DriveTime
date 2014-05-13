package com.cjb.drivetime;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

//simple class that just has one member property as an example
public class NewTripData implements Parcelable {
	private String _car;
	private String _logbook;
	private Date _startTime;
	private int _startOdometer;
	
	public NewTripData(String car, String logbook, Date startTime, int startOdometer){
		_car = car;
		_logbook = logbook;
		_startTime = startTime;
		_startOdometer = startOdometer;
	}
	
	private NewTripData(Parcel in) {
	   _car = in.readString();
	   _logbook = in.readString();
	   long startMillis = in.readLong();
	   _startOdometer = in.readInt();
	   _startTime = new Date(startMillis);
	}

	public void writeToParcel(Parcel out, int flags) {
	   out.writeString(_car);
	   out.writeString(_logbook);
	   out.writeLong(_startTime.getTime());
	   out.writeInt(_startOdometer);
	}
	
	public int describeContents() {
		   return 0;
		}

	public static final Parcelable.Creator<NewTripData> CREATOR = new Parcelable.Creator<NewTripData>() {
	   public NewTripData createFromParcel(Parcel in) {
	       return new NewTripData(in);
	   }
	
	   public NewTripData[] newArray(int size) {
	       return new NewTripData[size];
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
	public int getStartOdometer() {
		return _startOdometer;
	}
}