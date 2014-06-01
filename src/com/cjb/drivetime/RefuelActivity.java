package com.cjb.drivetime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjb.dataentrylistview.DataEntryDateListItem;
import com.cjb.dataentrylistview.DataEntryListItem;
import com.cjb.dataentrylistview.DataEntryListItemAdapter;
import com.cjb.dataentrylistview.DataEntryListListItem;
import com.cjb.dataentrylistview.DataEntryStringListItem;
import com.cjb.dataentrylistview.DataEntryTimeListItem;
import com.cjb.drivetime.Entity.Refuel;

public class RefuelActivity extends Activity {

	private List<DataEntryListItem> _items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refuel);
				
		_items = buildListItems(new Date());

		final ListView listview = (ListView) findViewById(R.id.refuel_data_entry_list_view);
		final ArrayAdapter<DataEntryListItem> adapter = 
				new DataEntryListItemAdapter(this, android.R.layout.simple_list_item_1, _items);
		listview.setAdapter(adapter);
	}


	private List<DataEntryListItem> buildListItems(Date timeStart) {
		List<DataEntryListItem> items = new ArrayList<DataEntryListItem>();
		//TODO: Get values from DB
		items.add(new DataEntryListListItem("Car", "Default car", new String[]{"Default car", "Other car"}));
		items.add(new DataEntryDateListItem("Date", timeStart));
		items.add(new DataEntryTimeListItem("Time", timeStart));
		items.add(new DataEntryStringListItem("Odometer", "0"));
		items.add(new DataEntryStringListItem("Volume", "0"));
		items.add(new DataEntryStringListItem("Cost", "0"));
		return items;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.refuel, menu);
		return true;
	}
	
	public void buttonDoneClick(View view){
		//String car = ((DataEntryListListItem)_items.get(0)).GetValue();
		Date refuelDateAndTime = CombineDateAndTime(((DataEntryDateListItem)_items.get(1)).GetValue(), ((DataEntryTimeListItem)_items.get(2)).GetValue());
		int odometerReading = Integer.parseInt(((DataEntryStringListItem)_items.get(3)).GetValue());
		int volume = Integer.parseInt(((DataEntryStringListItem)_items.get(4)).GetValue());
		int cost = Integer.parseInt(((DataEntryStringListItem)_items.get(5)).GetValue());
			
		Refuel refuel = new Refuel();
		refuel.setDate(refuelDateAndTime);
		//refuel.setCarId((Spinner) findViewById(R.id.spinner_car)));
		refuel.setOdometerReading(odometerReading);
		refuel.setCost(cost);
		refuel.setVolume(volume);

		MainActivity.GlobalRefuelRepository.Save(refuel);
		
		Intent intent = new Intent(getBaseContext(), MainActivity.class);
		startActivity(intent);
	}

	private Date CombineDateAndTime(Date date, Date time) {
		Calendar dateCalendar = GregorianCalendar.getInstance();
		dateCalendar.setTime(date);
		
		Calendar timeCalendar = GregorianCalendar.getInstance();
		timeCalendar.setTime(time);
		
		Calendar refuelCalendar = GregorianCalendar.getInstance();
		refuelCalendar.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
		refuelCalendar.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
		refuelCalendar.set(Calendar.DAY_OF_MONTH, dateCalendar.get(Calendar.DAY_OF_MONTH));
		refuelCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
		refuelCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
		refuelCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		return refuelCalendar.getTime();
	}
}
