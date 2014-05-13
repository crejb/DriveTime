package com.cjb.drivetime.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cjb.drivetime.R;
import com.cjb.drivetime.Entity.Refuel;
import com.cjb.drivetime.Entity.Trip;

public class ViewLogListItemAdapter extends ArrayAdapter<ViewLogListItem> {

	private List<ViewLogListItem> _trips;
	private LayoutInflater _layoutInflator;

	public ViewLogListItemAdapter(Context context, int textViewResourceId, List<ViewLogListItem> trips){
		super(context, textViewResourceId, trips);
		_trips = trips;
		_layoutInflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getViewTypeCount() {
		return 3;
	}
	
	@Override
	public int getItemViewType(int position) {
		ViewLogListItem listItem = _trips.get(position);
		
		switch(listItem.getListItemType()){
			case Trip:
				return 0;
			case DateDivider:
				return 1;
			case Refuel:
				return 2;
		}
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int viewType = getItemViewType(position);
		
		if(convertView == null){
			switch(viewType){
				case 0:
					convertView = _layoutInflator.inflate(R.layout.layout_view_log_trip_item, null);
					convertView.setTag(new ViewHolder_Trip(convertView));
					break;
				case 1:
					convertView = _layoutInflator.inflate(R.layout.layout_view_log_date_item, null);
					convertView.setTag(new ViewHolder_DateHeader(convertView));
					break;
				case 2:
					convertView = _layoutInflator.inflate(R.layout.layout_view_log_refuel_item, null);
					convertView.setTag(new ViewHolder_Refuel(convertView));
					break;
			}
		}

		ViewLogListItem listItem = _trips.get(position);
		
		IViewHolder viewHolder = (IViewHolder)convertView.getTag();
		viewHolder.populateView(listItem);

		return convertView;
	}
	
	private interface IViewHolder{
		void populateView(ViewLogListItem listItem);
	}
	
	private static class ViewHolder_Trip implements IViewHolder{
		private TextView txtStartTime;
		private TextView txtTravelTime;
		private TextView txtDistance;
		
		public ViewHolder_Trip(View view){
			txtStartTime = (TextView) view.findViewById(R.id.text_start_time);
			txtTravelTime = (TextView) view.findViewById(R.id.text_travel_time);
			txtDistance = (TextView) view.findViewById(R.id.text_distance);
		}
		
		public void populateView(ViewLogListItem listItem){
			Trip trip = listItem.getTrip();
			txtStartTime.setText(SimpleDateFormat.getTimeInstance().format(trip.getTimeStart()));
			txtTravelTime.setText(trip.getTimeElapsed().toShortTimeString());
			txtDistance.setText(trip.getDistanceTravelled() + "km");
		}
	}
	
	private static class ViewHolder_DateHeader implements IViewHolder{
		private TextView txtDate;
		
		public ViewHolder_DateHeader(View view){
			txtDate = (TextView) view.findViewById(R.id.text_date_header);
		}
		
		public void populateView(ViewLogListItem listItem){
			Date date = listItem.getDateHeader();
			txtDate.setText(SimpleDateFormat.getDateInstance().format(date));
		}
	}
	
	private static class ViewHolder_Refuel implements IViewHolder{
		private TextView txtRefuelTime;
		private TextView txtRefuelVolume;
		private TextView txtRefuelCost;
		
		public ViewHolder_Refuel(View view){
			txtRefuelTime = (TextView) view.findViewById(R.id.text_refuel_time);
			txtRefuelVolume = (TextView) view.findViewById(R.id.text_refuel_volume);
			txtRefuelCost = (TextView) view.findViewById(R.id.text_refuel_cost);
		}
		
		public void populateView(ViewLogListItem listItem){
			Refuel refuel = listItem.getRefuel();
			
			txtRefuelTime.setText(SimpleDateFormat.getTimeInstance().format(refuel.getDate()));
			txtRefuelVolume.setText(refuel.getVolume() + "L");
			txtRefuelCost.setText("$" + refuel.getCost());
		}
	}
}
