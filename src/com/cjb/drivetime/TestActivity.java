package com.cjb.drivetime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryDateListItem;
import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryListItem;
import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryListItemAdapter;
import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryListListItem;
import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryStringListItem;
import com.cjb.drivetime.ViewModel.DataEntryList.DataEntryTimeListItem;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		List<DataEntryListItem> items = buildListItems();

    	final ListView listview = (ListView) findViewById(R.id.list_view_data_entry);
    	final ArrayAdapter<DataEntryListItem> adapter = 
    			new DataEntryListItemAdapter(this, android.R.layout.simple_list_item_1, items);
    	listview.setAdapter(adapter);
	}

	private List<DataEntryListItem> buildListItems() {
		
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(1985, 12, 30, 2, 16);
		
		List<DataEntryListItem> items = new ArrayList<DataEntryListItem>();
		items.add(new DataEntryStringListItem("First Name", "Chris"));
		items.add(new DataEntryStringListItem("Last Name", "Bowles"));
		items.add(new DataEntryDateListItem("Birthday", calendar.getTime()));
		items.add(new DataEntryTimeListItem("Time", calendar.getTime()));
		items.add(new DataEntryListListItem("Favourite Colour", "Blue", new String[]{"Red", "Blue", "Yellow", "Green"}));
		items.add(new DataEntryStringListItem("Preference1", "Value1"));
		items.add(new DataEntryStringListItem("Preference2", "Value2"));
		items.add(new DataEntryStringListItem("Preference3", "Value3"));
		items.add(new DataEntryStringListItem("Preference4", "Value4"));
		items.add(new DataEntryStringListItem("Preference5", "Value5"));
		items.add(new DataEntryStringListItem("Preference6", "Value6"));
		items.add(new DataEntryStringListItem("Preference7", "Value7"));
		items.add(new DataEntryStringListItem("Preference8", "Value8"));
		items.add(new DataEntryStringListItem("Preference9", "Value9"));
		return items;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

}
