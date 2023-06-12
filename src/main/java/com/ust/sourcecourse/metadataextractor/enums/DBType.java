package com.ust.sourcecourse.metadataextractor.enums;

import org.springframework.lang.Nullable;

import lombok.Getter;

@Getter
public enum DBType {

	DB_MYSQL("mysql", "com.mysql.cj.jdbc.Driver", "TABLE_SCHEMA"),
	DB_POSTGRES("postgres", "org.postgresql.Driver", "TABLE_CATALOG"), DB_MONGODB("mongodb");

	private final String value;
	private final String driver;
	private final String schemaTableColumn;

	DBType(String value, String driver, String schemaTableColumn) {
		this.value = value;
		this.driver = driver;
		this.schemaTableColumn = schemaTableColumn;
	}

	DBType(String value, String driver) {
		this(value, driver, null);
	}

	DBType(String value) {
		this(value, null);
	}

	@Nullable
	public static DBType fromValue(String value) {
		for (DBType dbType : values()) {
			if (dbType.value.equalsIgnoreCase(value)) {
				return dbType;
			}
		}
		return null;
	}
}
