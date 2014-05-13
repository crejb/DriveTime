package com.cjb.drivetime.database;

import com.cjb.drivetime.database.DriveTimeDbHelper.DB_CONSTANTS;

public class RefuelTableHelper extends BaseTableHelper {

	public static final String TABLE_NAME = "refuel";
	
	public static final String COLUMN_NAME_REFUEL_ID = "refuel_id";
	public static final String COLUMN_NAME_DATE = "date";
	public static final String COLUMN_NAME_VOLUME = "volume";
	public static final String COLUMN_NAME_COST = "cost";
	public static final String COLUMN_NAME_CAR_ID = "car_id";
	public static final String COLUMN_NAME_ODOMETER = "odometer";
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public ColumnDefinition[] getColumnDefinitions() {
		return new ColumnDefinition[]{
				new ColumnDefinition(COLUMN_NAME_REFUEL_ID, DB_CONSTANTS.INTEGER, true, null),
				new ColumnDefinition(COLUMN_NAME_DATE, DB_CONSTANTS.INTEGER, false, null),
				new ColumnDefinition(COLUMN_NAME_VOLUME, DB_CONSTANTS.INTEGER, false, null),
				new ColumnDefinition(COLUMN_NAME_COST, DB_CONSTANTS.INTEGER, false, null),
				new ColumnDefinition(COLUMN_NAME_CAR_ID, DB_CONSTANTS.INTEGER, false, null), // TODO: Foreign Key for Car
				new ColumnDefinition(COLUMN_NAME_ODOMETER, DB_CONSTANTS.INTEGER, false, null),
		};
	}
}
