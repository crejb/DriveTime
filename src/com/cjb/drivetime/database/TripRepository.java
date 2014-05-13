package com.cjb.drivetime.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cjb.drivetime.Entity.TimeInterval;
import com.cjb.drivetime.Entity.Trip;
import com.cjb.drivetime.Repositories.ITripRepository;

public class TripRepository implements ITripRepository {
	
	DriveTimeDbHelper _dbHelper;
	TripTableHelper _tableHelper;
	
	public TripRepository(DriveTimeDbHelper dbHelper){
		_dbHelper = dbHelper;
		_tableHelper = new TripTableHelper();
	}
	
	@Override
	public Trip Get(Integer id) {
		return null;
	}

	@Override
	public void Save(Trip entity) {
		try{
			SQLiteDatabase db = _dbHelper.getWritableDatabase();
			
			// New value for one column
			ContentValues values = new ContentValues();
			if(entity.getId() == 0){
				values.putNull(TripTableHelper.COLUMN_NAME_TRIP_ID);
			}else{
				values.put(TripTableHelper.COLUMN_NAME_TRIP_ID, entity.getId());
			}
			
			//values.put(TripTableHelper.COLUMN_NAME_TIME_START, entity.getTimeStart().getTime());
			_dbHelper.putDateTimeInRecord(values, TripTableHelper.COLUMN_NAME_TIME_START, entity.getTimeStart());
			values.put(TripTableHelper.COLUMN_NAME_TIME_ELAPSED, entity.getTimeElapsed().getMillis());
			values.put(TripTableHelper.COLUMN_NAME_ODOMETER_START, entity.getStartOdometerReading());
			values.put(TripTableHelper.COLUMN_NAME_ODOMETER_END, entity.getEndOdometerReading());
	
			long result = db.insertWithOnConflict(TripTableHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		
		
	}

	@Override
	public void Delete(Trip entity) {
		String selection = TripTableHelper.COLUMN_NAME_TRIP_ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(entity.getId()) };
		
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		db.delete(TripTableHelper.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public List<Trip> GetAll() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();

		String sortOrder = TripTableHelper.COLUMN_NAME_TIME_START + " DESC";

		ColumnDefinition[] colDefs = _tableHelper.getColumnDefinitions();
		String[] colNames = new String[colDefs.length];
		for(int i=0; i<colDefs.length; i++){
			colNames[i] = colDefs[i].getName();
		}
		
		Cursor cursor = db.query(
			_tableHelper.getTableName(),
			colNames,
		    null,
		    null,
		    null,
		    null,
		    sortOrder
		    );
		
		ArrayList<Trip> tripList = new ArrayList<Trip>();
			
		while (cursor.moveToNext()) {
			Trip trip = LoadEntityFromCursor(cursor);
			tripList.add(trip);
		}
		
		cursor.close();
		
		return tripList;
	}
	
	private Trip LoadEntityFromCursor(Cursor cursor){
		int itemId = cursor.getInt(
			    cursor.getColumnIndexOrThrow(TripTableHelper.COLUMN_NAME_TRIP_ID)
			);
		//long startTimeMillis = cursor.getLong(
		//	    cursor.getColumnIndexOrThrow(TripTableHelper.COLUMN_NAME_TIME_START)
		//	);
		Date startTime = _dbHelper.getDateTimeFromRecord(cursor, TripTableHelper.COLUMN_NAME_TIME_START);
		long elapsedTimeMillis = cursor.getLong(
			    cursor.getColumnIndexOrThrow(TripTableHelper.COLUMN_NAME_TIME_ELAPSED)
			);
		int startOdometer = cursor.getInt(
			    cursor.getColumnIndexOrThrow(TripTableHelper.COLUMN_NAME_ODOMETER_START)
			);
		int endOdometer = cursor.getInt(
			    cursor.getColumnIndexOrThrow(TripTableHelper.COLUMN_NAME_ODOMETER_END)
			);
		
		return new Trip(itemId, startTime, new TimeInterval(elapsedTimeMillis), startOdometer, endOdometer);
	}

}
