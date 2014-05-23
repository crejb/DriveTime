package com.cjb.drivetime.ViewModel.DataEntryList;

import android.content.Context;

public interface DataEntryListItem extends Comparable<DataEntryListItem> {
	
	String GetDescription();
	String GetDisplayValue();
	void DisplayValueEditor(Context context, OnEditCompleteListener onEditCompleteListener);
	
	public interface OnEditCompleteListener{
		void OnEditComplete();
	}
}

