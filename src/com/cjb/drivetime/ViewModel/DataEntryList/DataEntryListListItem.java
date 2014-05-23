package com.cjb.drivetime.ViewModel.DataEntryList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DataEntryListListItem implements DataEntryListItem {

	
	private String _originalValue;
	private String _value;
	private String[] _listItems;
	private String _description;
	
	public DataEntryListListItem(String description, String value, String[] listItems){
		_description = description;
		_originalValue = value;
		_value = value;
		_listItems = listItems;
	}
	
	@Override
	public String GetDescription() {
		return _description;
	}

	public String GetValue() {
		return _value;
	}

	public String GetOriginalValue() {
		return _originalValue;
	}

	public void SetValue(String value) {
		_value = value;
	}

	@Override
	public String GetDisplayValue() {
		return _value;
	}

	@Override
	public void DisplayValueEditor(Context context, final OnEditCompleteListener onEditCompleteListener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setTitle(_description)
	           .setItems(_listItems, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int which) {
	            	   if(which >= 0 && which < _listItems.length){
	            		   SetValue(_listItems[which]);
	            	   }
	            	   onEditCompleteListener.OnEditComplete();
	               }
	           });
	    builder.show();
	}
	
	@Override
	public int compareTo(DataEntryListItem another) {
		return GetDescription().compareTo(another.GetDescription());
	}
}
