package com.cjb.drivetime.database;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DriveTimeDbHelper extends SQLiteOpenHelper{
	
	final boolean CLEAR_DATABASE_BE_CAREFUL = false;
	
	public static class DB_CONSTANTS{
		public static final String TEXT = "TEXT";
		public static final String INTEGER = "INTEGER";
		public static final String DATETIME = "TEXT";
		
		public static final String PRIMARY_KEY = "PRIMARY KEY";
		public static final String FOREIGN_KEY = "FOREIGN KEY";
	}
	
	protected static final String DATABASE_NAME = "DriveTime.db";
	protected static final int DATABASE_VERSION = 1;
	
	private List<BaseTableHelper> tableDefinitions = new ArrayList<BaseTableHelper>();
	private SimpleDateFormat _dateFormat; 
	
	public DriveTimeDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		_dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		
		ResetDatabaseIfRequired(context);
		
		tableDefinitions.add(new TripTableHelper());
		tableDefinitions.add(new RefuelTableHelper());
	}
	
	private void ResetDatabaseIfRequired(Context context) {
		if(CLEAR_DATABASE_BE_CAREFUL){
			File mDatabaseFile = context.getDatabasePath(DATABASE_NAME);
			mDatabaseFile.delete();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for(BaseTableHelper table : tableDefinitions){
			String sqlCreateEntries = generateCreateSql(table); 
			db.execSQL(sqlCreateEntries);
		}
	}
	
	public String generateCreateSql(BaseTableHelper table){
		ColumnDefinition[] columnDefinitions = table.getColumnDefinitions();
		
		String sql = "CREATE TABLE " + table.getTableName() + " (";
				
		for(ColumnDefinition column : columnDefinitions){
			sql += column.getName() + " " + column.getType();
			if(column.isPrimaryKey()){
				sql += " " + DB_CONSTANTS.PRIMARY_KEY;
			}
			sql += ",";
		}
		
		for(ColumnDefinition column : columnDefinitions){
			String foreignKey = column.getForeignKey();
			if(foreignKey != null){
				sql += "FOREIGN KEY (" + column.getName() + ") REFERENCES (" + foreignKey + "),";
			}
		}
		
		sql = sql.substring(0, sql.length() - 1);
		sql += ")";
		return sql;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void putDateTimeInRecord(ContentValues values, String columnName, Date dateTime){
        String dateString = _dateFormat.format(dateTime);
        
		values.put(columnName, dateString);
	}
	
	public Date getDateTimeFromRecord(Cursor cursor, String columnName){
		String dateTimeString = cursor.getString(
			    cursor.getColumnIndexOrThrow(columnName)
			);
		try {
			return _dateFormat.parse(dateTimeString);
		} catch (ParseException e) {
			return new Date();
		}
	}
}
