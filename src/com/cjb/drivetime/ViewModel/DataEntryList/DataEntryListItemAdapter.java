package com.cjb.drivetime.ViewModel.DataEntryList;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cjb.drivetime.R;
import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryListItem.OnEditCompleteListener;

public class DataEntryListItemAdapter extends ArrayAdapter<DataEntryListItem> {
	private List<DataEntryListItem> _items;
	private LayoutInflater _layoutInflator;
	private Context _context;

	public DataEntryListItemAdapter(Context context, int textViewResourceId, List<DataEntryListItem> items){
		super(context, textViewResourceId, items);
		_items = items;
		_layoutInflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		_context = context;
	}
	
	@Override
	public int getViewTypeCount() {
		return 1;
	}
	
	@Override
	public int getItemViewType(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = _layoutInflator.inflate(R.layout.layout_data_entry_list_item, null);
			convertView.setTag(new ViewHolder(convertView));			
		}

		DataEntryListItem listItem = _items.get(position);
		ViewHolder viewHolder = (ViewHolder)convertView.getTag();
		viewHolder.populateView(listItem);
		return convertView;
	}
	
	private class ViewHolder{
		private View _listItemView;
		private TextView _txtDescription;
		private TextView _txtValue;
		
		public ViewHolder(View view){
			_listItemView = view;
			_txtDescription = (TextView) view.findViewById(R.id.label_description);
			_txtValue = (TextView) view.findViewById(R.id.label_value);
		}
		
		public void populateView(final DataEntryListItem listItem){
			final OnEditCompleteListener onEditCompleteListener = new OnEditCompleteListener() {
				@Override
				public void OnEditComplete() {
					_txtValue.setText(listItem.GetDisplayValue());
				}
			}; 
			
			_listItemView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						listItem.DisplayValueEditor(_context, onEditCompleteListener);
					}
	            });
			
			_txtDescription.setText(listItem.GetDescription());
			_txtValue.setText(listItem.GetDisplayValue());
		}
	}
}
