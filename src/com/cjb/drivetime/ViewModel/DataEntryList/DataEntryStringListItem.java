package com.cjb.drivetime.ViewModel.DataEntryList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

public class DataEntryStringListItem implements DataEntryListItem {

	private String _originalValue;
	private String _value;
	private String _description;
	
	public DataEntryStringListItem(String description, String value){
		_description = description;
		_originalValue = value;
		_value = value;
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
		final EditText input = new EditText(context);
		input.setText(_value);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		input.setFocusable(true);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(_description);
		builder.setView(input);
		
		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	SetValue(input.getText().toString());
		    	onEditCompleteListener.OnEditComplete();
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		AlertDialog dialog = builder.create();
		dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		input.requestFocus();
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		dialog.show();
	}
	
	@Override
	public int compareTo(DataEntryListItem another) {
		return GetDescription().compareTo(another.GetDescription());
	}

}
