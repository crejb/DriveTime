package com.cjb.drivetime.ViewModel.DataEntryList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryListItem.OnEditCompleteListener;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.TimePicker;

public class DataEntryTimeListItem implements DataEntryListItem {

	private Date _originalValue;
	private Date _value;
	private String _description;
	
	public DataEntryTimeListItem(String description, Date value){
		_description = description;
		_originalValue = value;
		_value = value;
	}
	
	@Override
	public String GetDescription() {
		return _description;
	}

	public Date GetValue() {
		return _value;
	}

	public Date GetOriginalValue() {
		return _originalValue;
	}

	public void SetValue(Date value) {
		_value = value;
	}

	@Override
	public String GetDisplayValue() {
		return SimpleDateFormat.getTimeInstance().format(_value);
	}

	@Override
	public void DisplayValueEditor(Context context, final OnEditCompleteListener onEditCompleteListener) {
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(_value);
		OnTimeSetListener timeSetCallback = new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				calendar.set(Calendar.MINUTE, minute);
				SetValue(calendar.getTime());
				onEditCompleteListener.OnEditComplete();
			}
		};
		TimePickerDialog datePicker = new TimePickerDialog(context, timeSetCallback, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
		datePicker.show();
	}
	
	@Override
	public int compareTo(DataEntryListItem another) {
		return GetDescription().compareTo(another.GetDescription());
	}

}
