package com.ust.sourcecourse.metadataextractor.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ust.sourcecourse.metadataextractor.entity.ConnectionInfo;
import com.ust.sourcecourse.metadataextractor.entity.DataSource;
import com.ust.sourcecourse.metadataextractor.entity.SourceColumn;
import com.ust.sourcecourse.metadataextractor.entity.SourceTable;
import com.ust.sourcecourse.metadataextractor.enums.DBType;
import com.ust.sourcecourse.metadataextractor.repository.DataSourceRepository;
import com.ust.sourcecourse.metadataextractor.util.DBUtil;

@Service
public class MetaDataService {

	private static final String RELATION_SIZE = "RELATION_SIZE";
	private static final String TABLE_SCHEM = "TABLE_SCHEM";
	private static final String INDEX_LENGTH = "INDEX_LENGTH";
	private static final String DATA_LENGTH = "DATA_LENGTH";
	private static final String TABLE = "TABLE";
	private static final String TABLE_NAME = "TABLE_NAME";
	private static final String YES = "YES";
	private static final String COLUMN_DEF = "COLUMN_DEF";
	private static final String IS_NULLABLE = "IS_NULLABLE";
	private static final String TYPE_NAME = "TYPE_NAME";
	private static final String COLUMN_NAME = "COLUMN_NAME";

	@Autowired
	private DataSourceRepository dataSourceRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional
	public void getMetadata(Long uid) {
		DataSource dataSource = dataSourceRepository.findById(uid).orElseThrow();
		connectToDBAndGetData(dataSource);
		dataSourceRepository.save(dataSource);
	}

	@Transactional
	public void getMetadata() {
		List<DataSource> dataSources = dataSourceRepository.findAll();
		for (DataSource dataSource : dataSources) {
			connectToDBAndGetData(dataSource);
		}
		dataSourceRepository.saveAll(dataSources);
	}

	private void connectToDBAndGetData(DataSource dataSource) {
		try {
			ConnectionInfo connectionInfo = dataSource.getConnectionInfo();
			String connectionURL = connectionInfo.getConnectionURL();
			DBType dbType = DBUtil.getDBType(connectionURL);
			String dbName = dataSource.getName();
			try (Connection connection = DBUtil.connectUsingDriver(dbType, connectionInfo.getConnectionURL(),
					connectionInfo.getUsername(), connectionInfo.getPassword())) {
				DatabaseMetaData metaData = connection.getMetaData();
				dataSource.setStatus("Active");
				List<SourceTable> sourceTables = dataSource.getSourceTables();
				if (sourceTables == null) {
					sourceTables = new ArrayList<>();
				}
				Double dbSize = 0D;
				dbSize = extractTableMetadata(dataSource, dbType, dbName, connection, metaData, sourceTables, dbSize);
				dataSource.setTotalTables(sourceTables.size());
				dataSource.setSize(DBUtil.getSizeText(dbSize));
				dataSource.setSourceTables(sourceTables);
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Double extractTableMetadata(DataSource dataSource, DBType dbType, String dbName, Connection connection,
			DatabaseMetaData metaData, List<SourceTable> sourceTables, Double dbSize) throws SQLException {
		try (ResultSet tableRS = metaData.getTables(dbName, null, null, new String[] { TABLE })) {
			while (tableRS.next()) {
				String tableName = tableRS.getString(TABLE_NAME);
				Optional<SourceTable> sourceTble = sourceTables.stream()
						.filter(st -> st.getName().equalsIgnoreCase(tableName)).findFirst();
				Double tblSize = getTableSize(dbType, dbName, connection, tableRS, tableName);
				String tableSize = null;
				if (tblSize != null) {
					dbSize += tblSize;
					tableSize = DBUtil.getSizeText(tblSize);
				}
				String countQuery = DBUtil.getCountQuery(dbType, dbName, tableName);
				ResultSet rowsRS = DBUtil.getResultSet(connection, countQuery);
				Long rowCount = 0L;
				while (rowsRS.next()) {
					rowCount = Long.valueOf(rowsRS.getInt("ROW_COUNT"));
				}
				SourceTable sourceTable = getSourceTable(dataSource, sourceTables, tableName, sourceTble, rowCount,
						tableSize);
				extractColumnMetaData(dbName, metaData, tableName, sourceTable);
				collectSampleData(dbType, connection, dataSource, sourceTable);
			}
			tableRS.close();
		}
		return dbSize;
	}

	private Double getTableSize(DBType dbType, String dbName, Connection connection, ResultSet tableRS,
			String tableName) throws SQLException {
		Double tblSize = null;
		if (dbType.equals(DBType.DB_MYSQL)) {
			String sql = "SELECT * FROM information_schema.TABLES where UPPER(" + dbType.getSchemaTableColumn()
					+ ") = UPPER('" + dbName + "') AND" + " UPPER(TABLE_NAME) = UPPER('" + tableName + "');";
			ResultSet sizeRS = DBUtil.getResultSet(connection, sql);
			while (sizeRS.next()) {
				Double dataLength = sizeRS.getDouble(DATA_LENGTH);
				Double indexLength = sizeRS.getDouble(INDEX_LENGTH);
				tblSize = DBUtil.getSize(dataLength, indexLength);
			}
		} else if (dbType.equals(DBType.DB_POSTGRES)) {
			String schema = tableRS.getString(TABLE_SCHEM);
			String sqlSize = """
					SELECT
					  table_name AS TABLE_NAME,
					  pg_size_pretty(pg_relation_size(quote_ident(table_name))) AS SIZE_PRETTY,
					  pg_relation_size(quote_ident(table_name)) AS RELATION_SIZE
					from information_schema.TABLES
					where UPPER(TABLE_CATALOG) = '""" + dbName + """
					' AND table_schema = '""" + schema + """
					' AND UPPER(TABLE_NAME) = UPPER('""" + tableName + """
					');
					""";
			ResultSet sizeRS = DBUtil.getResultSet(connection, sqlSize);
			while (sizeRS.next()) {
				tblSize = DBUtil.bytesToMB(sizeRS.getDouble(RELATION_SIZE));
			}
		}
		return tblSize;
	}

	private void extractColumnMetaData(String dbName, DatabaseMetaData metaData, String tableName,
			SourceTable sourceTable) throws SQLException {
		try (ResultSet columnRS = metaData.getColumns(dbName, null, tableName, null)) {

			List<String> primaryKeys = getPrimaryKeysOfTable(dbName, metaData, tableName);
			List<String> uniqueKeys = getUniqueKeyOfTable(dbName, metaData, tableName);
			List<SourceColumn> sourceColumns = sourceTable.getSourceColumns();
			if (sourceColumns == null) {
				sourceColumns = new ArrayList<>();
			}

			getColumnMetadata(sourceTable, columnRS, primaryKeys, uniqueKeys, sourceColumns);
			columnRS.close();
			sourceTable.setSourceColumns(sourceColumns);
		}
	}

	private List<String> getUniqueKeyOfTable(String dbName, DatabaseMetaData metaData, String tableName)
			throws SQLException {
		List<String> uniqueKeys = new ArrayList<>();
		ResultSet uniqueRS = metaData.getIndexInfo(dbName, null, tableName, true, true);
		while (uniqueRS.next()) {
			String key = uniqueRS.getString(COLUMN_NAME);
			uniqueKeys.add(key);
		}
		return uniqueKeys;
	}

	private List<String> getPrimaryKeysOfTable(String dbName, DatabaseMetaData metaData, String tableName)
			throws SQLException {
		List<String> primaryKeys = new ArrayList<>();
		ResultSet primaryKeysRS = metaData.getPrimaryKeys(dbName, null, tableName);
		while (primaryKeysRS.next()) {
			String key = primaryKeysRS.getString(COLUMN_NAME);
			primaryKeys.add(key);
		}
		return primaryKeys;
	}

	private void getColumnMetadata(SourceTable sourceTable, ResultSet columnRS, List<String> primaryKeys,
			List<String> uniqueKeys, List<SourceColumn> sourceColumns) throws SQLException {
		while (columnRS.next()) {
			String columnName = columnRS.getString(COLUMN_NAME);
			String typeName = columnRS.getString(TYPE_NAME);
			String nullable = columnRS.getString(IS_NULLABLE);
			String defValue = columnRS.getString(COLUMN_DEF);
			boolean isPrimary = primaryKeys.contains(columnName);
			boolean isUnique = uniqueKeys.contains(columnName);
			boolean isNullable = nullable != null && nullable.equalsIgnoreCase(YES);
			Optional<SourceColumn> sourceClmn = sourceColumns.stream()
					.filter(sc -> sc.getName().equalsIgnoreCase(columnName)).findFirst();
			SourceColumn sourceColumn = null;
			if (sourceClmn.isPresent()) {
				sourceColumn = sourceClmn.get();
				sourceColumn.setName(columnName);
				sourceColumn.setType(typeName);
				sourceColumn.setDefaultValue(defValue);
				sourceColumn.setNullable(isNullable);
				sourceColumn.setPrimary(isPrimary);
				sourceColumn.setUnique(isUnique);
			} else {
				sourceColumn = SourceColumn.builder().sourceTable(sourceTable).name(columnName).type(typeName)
						.defaultValue(defValue).isNullable(isNullable).isPrimary(isPrimary).isUnique(isUnique).build();
				sourceColumns.add(sourceColumn);
			}
		}
	}

	private SourceTable getSourceTable(DataSource dataSource, List<SourceTable> sourceTables, String tableName,
			Optional<SourceTable> sourceTble, Long rowCount, String tbleSize) {
		SourceTable sourceTable = null;
		if (sourceTble.isPresent()) {
			sourceTable = sourceTble.get();
			sourceTable.setName(tableName);
			sourceTable.setRowCount(rowCount);
			sourceTable.setSize(tbleSize);
		} else {
			sourceTable = SourceTable.builder().name(tableName).rowCount(rowCount).size(tbleSize).dataSource(dataSource)
					.build();
			sourceTables.add(sourceTable);
		}
		return sourceTable;
	}

	private void collectSampleData(DBType dbType, Connection connection, DataSource dataSource, SourceTable sourceTable)
			throws SQLException {
		String sql = DBUtil.getSelectLimitQuery(dbType, dataSource.getName(), sourceTable.getName());
		ResultSet resultSet = DBUtil.getResultSet(connection, sql);
		List<SourceColumn> sourceColumns = sourceTable.getSourceColumns();
		ArrayNode arrayNode = objectMapper.createArrayNode();
		while (resultSet.next()) {
			ObjectNode objectNode = objectMapper.createObjectNode();
			sourceColumns.forEach(sourceColumn -> {
				String name = sourceColumn.getName();
				String value = null;
				try {
					value = resultSet.getString(name);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				objectNode.put(name, value);
			});
			arrayNode.add(objectNode);
		}
		String json = arrayNode.toString();
		sourceTable.setSampleData(json);
	}

}
