package com.cjb.drivetime.database;


public abstract class BaseTableHelper {
	public abstract String getTableName();
	public abstract ColumnDefinition[] getColumnDefinitions();
}
