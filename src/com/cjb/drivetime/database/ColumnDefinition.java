package com.cjb.drivetime.database;

public class ColumnDefinition {
	private String name;
	private String type;
	private boolean isPrimaryKey;
	private String foreignKey;

	public ColumnDefinition(String name, String type, boolean isPrimaryKey, String foreignKeyTable){
		this.name = name;
		this.type = type;
		this.isPrimaryKey = isPrimaryKey;
		this.foreignKey = foreignKeyTable;
	}
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public String getForeignKey() {
		return foreignKey;
	}
}
