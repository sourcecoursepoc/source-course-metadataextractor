package com.ust.sourcecourse.metadataextractor.util;

import static com.ust.sourcecourse.metadataextractor.constant.AppConstant.JDBC;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.ust.sourcecourse.metadataextractor.enums.DBType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBUtil {

	public static DBType getDBType(String connectionURL) {
		String cleanURI = connectionURL;
		if (connectionURL.startsWith(JDBC)) {
			cleanURI = connectionURL.substring(JDBC.length());
		}
		URI uri = URI.create(cleanURI);
		String dbEngine = uri.getScheme();
		DBType dbType = null;
		if (dbEngine.contains(DBType.DB_MYSQL.getValue())) {
			dbType = DBType.DB_MYSQL;
		} else if (dbEngine.contains(DBType.DB_POSTGRES.getValue())) {
			dbType = DBType.DB_POSTGRES;
		} else {
			log.info("Yet to configure for {}", dbEngine);
		}
		return dbType;
	}

	public static Connection connectUsingDriver(DBType dbType, String connectionURL, String username, String password)
			throws ClassNotFoundException, SQLException {
		String driver = dbType.getDriver();
		Class.forName(driver);
		Connection connection = null;
		if (StringUtils.isAnyBlank(username, password)) {
			connection = DriverManager.getConnection(connectionURL);
		} else {
			connection = DriverManager.getConnection(connectionURL, username, password);
		}
		return connection;
	}

	public static String getCountQuery(DBType dbType, String dbName, String tableName) {
		String fromClause = getFromClause(dbType, dbName, tableName);
		return "SELECT count(*) as ROW_COUNT FROM " + fromClause + ";";
	}

	private static String getFromClause(DBType dbType, String dbName, String tableName) {
		return dbType.equals(DBType.DB_MYSQL) ? dbName + "." + tableName : "\"" + tableName + "\"";
	}

	public static String getSelectLimitQuery(DBType dbType, String dbName, String tableName) {
		String fromClause = getFromClause(dbType, dbName, tableName);
		String sql = "SELECT * FROM " + fromClause + " LIMIT 10;";

		return sql;
	}

	public static ResultSet getResultSet(Connection connection, String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		return statement.executeQuery();
	}

	public static Double getSize(Double dataLength, Double indexLength) {
		return bytesToMB(dataLength + indexLength);
	}

	public static Double bytesToMB(Double sizeInBytes) {
		return sizeInBytes / 1024 / 1024;
	}

	public static String getSizeText(Double size) {
		return String.format("%.2f MB", size);
	}
}
