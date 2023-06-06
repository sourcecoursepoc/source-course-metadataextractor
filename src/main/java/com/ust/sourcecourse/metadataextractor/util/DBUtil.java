package com.ust.sourcecourse.metadataextractor.util;

import static com.ust.sourcecourse.metadataextractor.constant.AppConstant.DB_MONGODB;
import static com.ust.sourcecourse.metadataextractor.constant.AppConstant.DB_MYSQL;
import static com.ust.sourcecourse.metadataextractor.constant.AppConstant.DB_POSTGRES;
import static com.ust.sourcecourse.metadataextractor.constant.AppConstant.JDBC;

import java.net.URI;

public class DBUtil {

	private static final String DRIVER_MONGODB = "com.mongodb.jdbc.MongoDriver";
	private static final String DRIVER_POSTGRESSQL = "org.postgresql.Driver";
	private static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";

	public static String getDBType(String connectionURL) {
		String cleanURI = connectionURL;
		if (connectionURL.contains(JDBC)) {
			cleanURI = connectionURL.substring(JDBC.length());
		}
		URI uri = URI.create(cleanURI);
		String dbEngine = uri.getScheme();
		String dbType = "";
		if (dbEngine.contains(DB_MYSQL)) {
			dbType = DB_MYSQL;
		} else if (dbEngine.contains(DB_POSTGRES)) {
			dbType = DB_POSTGRES;
		} else if (dbEngine.contains(DB_MONGODB)) {
			dbType = DB_MONGODB;
		}

		return dbType;
	}

	public static String getConnectionDriver(String dbType) {
		String driver = DRIVER_MYSQL;
		driver = switch (dbType) {
		case DB_MYSQL -> DRIVER_MYSQL;
		case DB_POSTGRES -> DRIVER_POSTGRESSQL;
		case DB_MONGODB -> DRIVER_MONGODB;
		default -> throw new IllegalArgumentException("Unexpected value: " + dbType);
		};
		return driver;
	}
}
