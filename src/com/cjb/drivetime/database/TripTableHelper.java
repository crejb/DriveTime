package com.cjb.drivetime.database;

import com.cjb.drivetime.database.DriveTimeDbHelper.DB_CONSTANTS;

public class TripTableHelper extends BaseTableHelper{

	public static final String TABLE_NAME = "trip";
	
    public static final String COLUMN_NAME_TRIP_ID = "trip_id";
    public static final String COLUMN_NAME_TIME_START = "time_start";
    public static final String COLUMN_NAME_TIME_ELAPSED = "time_elapsed";
    public static final String COLUMN_NAME_ODOMETER_START = "odometer_start";
    public static final String COLUMN_NAME_ODOMETER_END = "odometer_end";

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public ColumnDefinition[] getColumnDefinitions() {
		return new ColumnDefinition[]{
				new ColumnDefinition(COLUMN_NAME_TRIP_ID, DB_CONSTANTS.INTEGER, true, null),
				new ColumnDefinition(COLUMN_NAME_TIME_START, DB_CONSTANTS.TEXT, false, null),
				new ColumnDefinition(COLUMN_NAME_TIME_ELAPSED, DB_CONSTANTS.INTEGER, false, null),
				new ColumnDefinition(COLUMN_NAME_ODOMETER_START, DB_CONSTANTS.INTEGER, false, null),
				new ColumnDefinition(COLUMN_NAME_ODOMETER_END, DB_CONSTANTS.INTEGER, false, null)
		};
	}
}
