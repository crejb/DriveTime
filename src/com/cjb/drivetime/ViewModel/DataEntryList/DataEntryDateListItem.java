package com.cjb.drivetime.ViewModel.DataEntryList;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.cjb.drivetime.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class DataEntryDateListItem implements DataEntryListItem {

	private Date _originalValue;
	private Date _value;
	private String _description;
	
	public DataEntryDateListItem(String description, Date value){
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
		return SimpleDateFormat.getDateInstance().format(_value);
	}

	@Override
	public void DisplayValueEditor(Context context, final OnEditCompleteListener onEditCompleteListener) {
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(_value);
		
		OnDateSetListener dateSetCallback = new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				SetValue(calendar.getTime());
				onEditCompleteListener.OnEditComplete();
			}
		};
		DatePickerDialog datePicker = new DatePickerDialog(context, dateSetCallback, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePicker.show();
	}
	
	@Override
	public int compareTo(DataEntryListItem another) {
		return GetDescription().compareTo(another.GetDescription());
	}

}
