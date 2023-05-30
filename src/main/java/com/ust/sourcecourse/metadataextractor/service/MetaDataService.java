package com.ust.sourcecourse.metadataextractor.service;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ust.sourcecourse.metadataextractor.entity.ConnectionInfo;
import com.ust.sourcecourse.metadataextractor.entity.DataSource;
import com.ust.sourcecourse.metadataextractor.entity.SourceColumn;
import com.ust.sourcecourse.metadataextractor.entity.SourceTable;
import com.ust.sourcecourse.metadataextractor.repository.DataSourceRepository;
@Service
public class MetaDataService {

	@Autowired
	private DataSourceRepository dataSourceRepository;

	public void getMetadata() {
		List<DataSource> dataSources = dataSourceRepository.findAll();
		for (DataSource dataSource : dataSources) {
			connectToDBAndGetData(dataSource);
		}
		dataSourceRepository.saveAll(dataSources);
	}

	private void connectToDBAndGetData(DataSource dataSource) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			ConnectionInfo connectionInfo = dataSource.getConnectionInfo();
			Connection connection = DriverManager.getConnection(connectionInfo.getConnectionURL(),
					connectionInfo.getUsername(), connectionInfo.getPassword());
			dataSource.setStatus("Active");
			getTableMetadata(connection, dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getTableMetadata(Connection connection, DataSource dataSource) throws SQLException {
		int totalTables = 0;
		String sql = "SELECT * FROM information_schema.TABLES where TABLE_SCHEMA = '" + dataSource.getName() + "'";
		ResultSet tableRS = getResultSet(connection, sql);

		List<SourceTable> sourceTables = dataSource.getSourceTables();
		if (sourceTables == null) {
			sourceTables = new ArrayList<>();
		}

		Double dbSize = 0D;
		while (tableRS.next()) {
			totalTables += 1;
			String tableName = tableRS.getString("TABLE_NAME");

			Optional<SourceTable> sourceTble = sourceTables.stream()
					.filter(st -> st.getName().equalsIgnoreCase(tableName)).findFirst();

			Long rowCount = tableRS.getLong("TABLE_ROWS");
			Double dataLength = tableRS.getDouble("DATA_LENGTH");
			Double indexLength = tableRS.getDouble("INDEX_LENGTH");
			Double tblSize = getSize(dataLength, indexLength);
			String tbleSizeInMB = getSizeText(tblSize);
			dbSize += tblSize;
			SourceTable sourceTable = getSourceTable(dataSource, sourceTables, tableName, sourceTble, rowCount,
					tbleSizeInMB);
			getColumnMetadata(connection, dataSource, sourceTable);
		}
		dataSource.setTotalTables(totalTables);
		dataSource.setSize(getSizeText(dbSize));
		dataSource.setSourceTables(sourceTables);
	}

	private SourceTable getSourceTable(DataSource dataSource, List<SourceTable> sourceTables, String tableName,
			Optional<SourceTable> sourceTble, Long rowCount, String tbleSizeInMB) {
		SourceTable sourceTable = null;
		if (sourceTble.isPresent()) {
			sourceTable = sourceTble.get();
			sourceTable.setName(tableName);
			sourceTable.setRowCount(rowCount);
			sourceTable.setSize(tbleSizeInMB);
		} else {
			sourceTable = SourceTable.builder().name(tableName).rowCount(rowCount).size(tbleSizeInMB)
					.dataSource(dataSource).build();
			sourceTables.add(sourceTable);
		}
		return sourceTable;
	}

	private void getColumnMetadata(Connection connection, DataSource dataSource, SourceTable sourceTable)
			throws SQLException {
		String sql = "SELECT * FROM information_schema.COLUMNS where TABLE_SCHEMA = '" + dataSource.getName()
				+ "' AND TABLE_NAME = '" + sourceTable.getName() + "'";
		ResultSet columnRS = getResultSet(connection, sql);
		List<SourceColumn> sourceColumns = sourceTable.getSourceColumns();
		if (sourceColumns == null) {
			sourceColumns = new ArrayList<>();
		}
		while (columnRS.next()) {
			String colName = columnRS.getString("COLUMN_NAME");

			String type = columnRS.getString("COLUMN_TYPE");
			String dfltValue = columnRS.getString("COLUMN_DEFAULT");
			boolean isNullable = columnRS.getBoolean("IS_NULLABLE");
			String colKey = columnRS.getString("COLUMN_KEY");
			boolean isPrimary = false;
			boolean isUnique = false;

			switch (colKey) {
				case "PRI": {
					isPrimary = true;
					isUnique = true;
					break;
				}
				case "UNI": {
					isUnique = true;
					break;
				}
			}
			Optional<SourceColumn> sourceClmn = sourceColumns.stream()
					.filter(sc -> sc.getName().equalsIgnoreCase(colName)).findFirst();
			SourceColumn sourceColumn = null;
			if (sourceClmn.isPresent()) {
				sourceColumn = sourceClmn.get();
				sourceColumn.setName(colName);
				sourceColumn.setType(type);
				sourceColumn.setDefaultValue(dfltValue);
				sourceColumn.setNullable(isNullable);
				sourceColumn.setPrimary(isPrimary);
				sourceColumn.setUnique(isUnique);
			} else {
				sourceColumn = SourceColumn.builder().sourceTable(sourceTable).name(colName).type(type)
						.defaultValue(dfltValue).isNullable(isNullable).isPrimary(isPrimary).isUnique(isUnique).build();
				sourceColumns.add(sourceColumn);
			}
		}
		sourceTable.setSourceColumns(sourceColumns);
	}
	

	public void getSelectedRows(Connection connection, DataSource dataSource, SourceTable sourceTable) {
	    String tableName = sourceTable.getName();
	    String sql = "SELECT * FROM " + tableName + " LIMIT 10";

	    try {
	        ResultSet tableRS = getResultSet(connection, sql);
	        List<SourceTable> sourceTables = dataSource.getSourceTables();
	        if (sourceTables == null) {
	            sourceTables = new ArrayList<>();
	        }

	        double dbSize = 0.0;
	        JSONArray sourceTablesArray = new JSONArray();
	        
	        while (tableRS.next()) {
	            String currentTableName = tableRS.getString("TABLE_NAME");

	            Optional<SourceTable> sourceTableOptional = sourceTables.stream()
	                    .filter(st -> st.getName().equalsIgnoreCase(currentTableName))
	                    .findFirst();

	            long rowCount = tableRS.getLong("TABLE_ROWS");
	            double dataLength = tableRS.getDouble("DATA_LENGTH");
	            double indexLength = tableRS.getDouble("INDEX_LENGTH");
	            double tblSize = getSize(dataLength, indexLength);
	            String tblSizeInMB = getSizeText(tblSize);
	            dbSize += tblSize;

	            SourceTable selectedSourceTable = getSourceTable(dataSource, sourceTables, currentTableName,
	                    sourceTableOptional, rowCount, tblSizeInMB);
	            getColumnMetadata(connection, dataSource, selectedSourceTable);

	            JSONObject sourceTableObject = new JSONObject();
	            sourceTableObject.put("name", selectedSourceTable.getName());
	            sourceTableObject.put("rowCount", selectedSourceTable.getRowCount());
	            sourceTableObject.put("size", selectedSourceTable.getSize());
	            
	            sourceTablesArray.put(sourceTableObject);
	        }

	        dataSource.setTotalTables(sourceTables.size());
	        dataSource.setSize(getSizeText(dbSize));
	        dataSource.setSourceTables(sourceTables);

	        JSONObject resultObject = new JSONObject();
	        resultObject.put("dataSource", dataSource);
	        resultObject.put("sourceTables", sourceTablesArray);

	        String jsonResult = resultObject.toString();
	        System.out.println(jsonResult); 
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private Double getSize(Double dataLength, Double indexLength) {
		return (dataLength + indexLength) / 1024 / 1024;
	}

	private String getSizeText(Double size) {
		return String.format("%.2f MB", size);
	}

	private ResultSet getResultSet(Connection connection, String sql) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		return statement.executeQuery();
	}
}