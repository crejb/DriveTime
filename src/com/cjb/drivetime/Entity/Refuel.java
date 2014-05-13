package com.cjb.drivetime.Entity;

import java.util.Date;


public class Refuel {
	private int _id;
	private Date _date;
	private int _volume;
	private int _cost;
	private int _odometerReading;
	private int _carId;
	
	public Refuel(){
		
	}
	
	public Refuel(int id, Date date, int volume, int cost, int odometer, int carId){
		setId(id);
		setDate(date);
		setVolume(volume);
		setCost(cost);
		setOdometerReading(odometer);
		setCarId(carId);
	}
	public int getId(){
		return _id;
	}
	public void setId(int id){
		_id = id;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date _date) {
		this._date = _date;
	}

	public int getVolume() {
		return _volume;
	}

	public void setVolume(int _volume) {
		this._volume = _volume;
	}

	public int getCost() {
		return _cost;
	}

	public void setCost(int _cost) {
		this._cost = _cost;
	}

	public int getOdometerReading() {
		return _odometerReading;
	}

	public void setOdometerReading(int _odometerReading) {
		this._odometerReading = _odometerReading;
	}

	public int getCarId() {
		return _carId;
	}

	public void setCarId(int _carId) {
		this._carId = _carId;
	}
	
	public String toString(){
		return String.format("Refuel Id:%d, Date:%s, Volume:%s, Cost:%s", 
				_id, _date.toString(), getVolume(), getCost());
	}
}
