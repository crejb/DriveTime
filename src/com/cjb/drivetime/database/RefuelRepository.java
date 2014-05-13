package com.cjb.drivetime.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cjb.drivetime.Entity.Refuel;
import com.cjb.drivetime.Entity.TimeInterval;
import com.cjb.drivetime.Entity.Trip;
import com.cjb.drivetime.Repositories.IRefuelRepository;

public class RefuelRepository implements IRefuelRepository {
	
	DriveTimeDbHelper _dbHelper;
	RefuelTableHelper _tableHelper;
	
	public RefuelRepository(DriveTimeDbHelper dbHelper){
		_dbHelper = dbHelper;
		_tableHelper = new RefuelTableHelper();
	}
	
	@Override
	public Refuel Get(Integer id) {
		return null;
	}

	@Override
	public void Save(Refuel entity) {
		try{
			SQLiteDatabase db = _dbHelper.getWritableDatabase();
			
			// New value for one column
			ContentValues values = new ContentValues();
			if(entity.getId() == 0){
				values.putNull(RefuelTableHelper.COLUMN_NAME_REFUEL_ID);
			}else{
				values.put(RefuelTableHelper.COLUMN_NAME_REFUEL_ID, entity.getId());
			}
			
			values.put(RefuelTableHelper.COLUMN_NAME_DATE, entity.getDate().getTime());
			values.put(RefuelTableHelper.COLUMN_NAME_VOLUME, entity.getVolume());
			values.put(RefuelTableHelper.COLUMN_NAME_COST, entity.getCost());
			values.put(RefuelTableHelper.COLUMN_NAME_ODOMETER, entity.getOdometerReading());
			values.put(RefuelTableHelper.COLUMN_NAME_CAR_ID, entity.getCarId());
	
			long result = db.insertWithOnConflict(RefuelTableHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
	}

	@Override
	public void Delete(Refuel entity) {
		String selection = RefuelTableHelper.COLUMN_NAME_REFUEL_ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(entity.getId()) };
		
		SQLiteDatabase db = _dbHelper.getReadableDatabase();
		db.delete(RefuelTableHelper.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public List<Refuel> GetAll() {
		SQLiteDatabase db = _dbHelper.getReadableDatabase();

		String sortOrder = RefuelTableHelper.COLUMN_NAME_DATE + " DESC";

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
		
		ArrayList<Refuel> refuelList = new ArrayList<Refuel>();
			
		while (cursor.moveToNext()) {
			Refuel refuel = LoadEntityFromCursor(cursor);
			refuelList .add(refuel);
		}
		
		cursor.close();
		
		return refuelList;
	}
	
	private Refuel LoadEntityFromCursor(Cursor cursor){
		int itemId = cursor.getInt(
			    cursor.getColumnIndexOrThrow(RefuelTableHelper.COLUMN_NAME_REFUEL_ID)
			);

		long dateMillis = cursor.getLong(
			    cursor.getColumnIndexOrThrow(RefuelTableHelper.COLUMN_NAME_DATE)
			);
		int volume = cursor.getInt(
			    cursor.getColumnIndexOrThrow(RefuelTableHelper.COLUMN_NAME_VOLUME)
			);
		int cost = cursor.getInt(
			    cursor.getColumnIndexOrThrow(RefuelTableHelper.COLUMN_NAME_COST)
			);
		int odometer = cursor.getInt(
			    cursor.getColumnIndexOrThrow(RefuelTableHelper.COLUMN_NAME_ODOMETER)
			);
		int carId = cursor.getInt(
			    cursor.getColumnIndexOrThrow(RefuelTableHelper.COLUMN_NAME_CAR_ID)
			);
		
		return new Refuel(itemId, new Date(dateMillis), volume, cost, odometer, carId);
	}
}
