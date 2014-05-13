package com.cjb.drivetime.ViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cjb.drivetime.Entity.Refuel;
import com.cjb.drivetime.Entity.Trip;

public class ViewLogListItem implements Comparable<ViewLogListItem> {
	private ListItemType _listItemType;
	private Trip _trip;
	private Refuel _refuel;
	private Date _dateHeader;
	
	public ViewLogListItem(Trip trip){
		_trip = trip;
		_listItemType = ListItemType.Trip;
	}
	
	public ViewLogListItem(Refuel refuel){
		_refuel = refuel;
		_listItemType = ListItemType.Refuel;
	}
	
	public ViewLogListItem(Date dateHeader){
		_dateHeader = dateHeader;
		_listItemType = ListItemType.DateDivider;
	}
	
	public ListItemType getListItemType(){
		return _listItemType;
	}
	
	public Trip getTrip() {
		return _trip;
	}
	
	public Refuel getRefuel() {
		return _refuel;
	}

	public Date getDateHeader() {
		return _dateHeader;
	}
	
	public enum ListItemType{
		Trip,
		DateDivider,
		Refuel
	}
	
	protected Date getDate(){
		switch(_listItemType){
			case Trip:
				return _trip.getTimeStart();
			case DateDivider:
				return _dateHeader;
			case Refuel:
				return _refuel.getDate();
		}
		return new Date();
	}

	/**
	 * Sorts from newest to oldest, with the date header coming before any detailed items
	 */
	@Override
	public int compareTo(ViewLogListItem other) {
		Date thisDate = getDate();
		Date otherDate = other.getDate();
		
		if(_listItemType == ListItemType.DateDivider || other._listItemType == ListItemType.DateDivider){
			thisDate = getDateWithoutTime(thisDate);
			otherDate = getDateWithoutTime(otherDate);
		}
		
		int result = thisDate.compareTo(otherDate);
		if (result != 0){
			// Sorting descending, so negate result
			return -result;
		}
		
		// Date Header comes first
		if(_listItemType == ListItemType.DateDivider){
			return -1;
		}
		if(other._listItemType == ListItemType.DateDivider){
			return 1;
		}
		
		return 0;
	}
	
	private Date getDateWithoutTime(Date date){
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    Date calDate = cal.getTime();
	    
	    Date stringDate = null;
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
	    try {
			stringDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			
		}
	    
	    return stringDate;
	}
}
