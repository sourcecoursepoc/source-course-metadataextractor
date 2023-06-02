package com.ust.sourcecourse.metadataextractor.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.client.ClientSession;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.ust.sourcecourse.metadataextractor.constant.AppConstant;
import com.ust.sourcecourse.metadataextractor.entity.ConnectionInfo;
import com.ust.sourcecourse.metadataextractor.entity.DataSource;
import com.ust.sourcecourse.metadataextractor.entity.SourceColumn;
import com.ust.sourcecourse.metadataextractor.entity.SourceTable;
import com.ust.sourcecourse.metadataextractor.repository.DataSourceRepository;
import com.ust.sourcecourse.metadataextractor.util.DBUtil;

@Service
public class MetaDataService {

	@Autowired
	private DataSourceRepository dataSourceRepository;

	@Autowired
	private ObjectMapper objectMapper;

//	@Transactional
	public void getMetadata(Long uid) {
//		DataSource dataSource = dataSourceRepository.findById(uid).orElseThrow();
//		connectToDBAndGetData(dataSource);
//		dataSourceRepository.save(dataSource);
	}

//	@Transactional
	public void getMetadata() {
		List<DataSource> dataSources = dataSourceRepository.findAll();
		for (DataSource dataSource : dataSources) {
			connectToDBAndGetData(dataSource);
		}
//		dataSourceRepository.saveAll(dataSources);
	}

	private void connectToDBAndGetData(DataSource dataSource) {
		try {

			ConnectionInfo connectionInfo = dataSource.getConnectionInfo();
			String connectionURL = connectionInfo.getConnectionURL();
			String dbType = DBUtil.getDBType(connectionURL);

			switch (dbType) {
			case AppConstant.DB_MYSQL:
			case AppConstant.DB_POSTGRES:
				connectUsingDriver(dataSource, connectionInfo, dbType);
				break;
			case AppConstant.DB_MONGODB:
				connectToMongoDB(dataSource, connectionInfo, dbType);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connectToMongoDB(DataSource dataSource, ConnectionInfo connectionInfo, String dbType) {
		String connectionURL = connectionInfo.getConnectionURL();
		try (MongoClient mongoClient = MongoClients.create(connectionURL);
				ClientSession clientSession = mongoClient.startSession();) {
//			List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
//			databases.forEach(db -> {
//				System.out.println(db.toJson());
//			});
//        }

//			MongoIterable<String> listDatabaseNames = mongoClient.listDatabaseNames();
//			MongoCursor<String> mongoCursor = listDatabaseNames.iterator();
//			while(mongoCursor.hasNext()) {
//				String next = mongoCursor.next();
//				System.out.println("Next => " + next);
//			}

			MongoDatabase database = mongoClient.getDatabase(dataSource.getName());

			ListCollectionsIterable<Document> listCollections = database.listCollections();
			MongoCursor<Document> iterator = listCollections.iterator();
			List<Document> documents = new ArrayList<>();
			while (iterator.hasNext()) {
				Document document = iterator.next();
				documents.add(document);
				String collectionName = document.getString("name");
				MongoCollection<Document> mongoCollection = database.getCollection(collectionName);
				long rows = mongoCollection.countDocuments();
				System.out.println("collectionName = " + collectionName + " , Rows = " + rows);
				Document first = mongoCollection.find().first();
//				System.out.println("first = " + first);
//				System.out.println("first JSON = " + first.toJson());
				Set<Entry<String, Object>> entrySet = first.entrySet();
				List<String> columns = new ArrayList<>();
				entrySet.forEach(entry -> {
					Object value = entry.getValue();
					String typeString = value == null ? "null" : value.getClass().toString();
//					System.out.println("Key = " + entry.getKey() + " : " + value + " , typeString = " + typeString);
					columns.add(entry.getKey());
				});
			}
			
			Bson command = new BsonDocument("dbStats", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            System.out.println("dbStats: " + commandResult.toJson());
            
//            command = new BsonDocument("collStats", new BsonInt64(1));
//            commandResult = database.runCommand(command);
//            System.out.println("collStats: " + commandResult.toJson());
            
            Document stats = database.runCommand(new Document("collStats", "cutstomers"));
            System.out.println(stats.get("count"));
            System.out.println(stats.get("avgObjSize"));
            System.out.println(stats.get("storageSize"));

		}

//		ConnectionString uri = new ConnectionString(connectionURL);
//		try(MongoConnection mongoConnection = new MongoConnection(
//				new MongoConnectionProperties(uri, dataSource.getName(), null, null, null, false));) {
//			DatabaseMetaData databaseMetaData = new MongoDatabaseMetaData(mongoConnection);
//			
//			PreparedStatement prepareStatement = mongoConnection.prepareStatement("db.getCollection(categories).dataSize()");
//			ResultSet resultSet = prepareStatement.executeQuery();
//			ResultSetMetaData metaData = resultSet.getMetaData();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		try {
//			ConnectionString uri = new ConnectionString(connectionURL);
//			DatabaseMetaData databaseMetaData = new MongoDatabaseMetaData(new MongoConnection(
//					new MongoConnectionProperties(uri, dataSource.getName(), null, null, null, false)));
//			ResultSet resultSet = databaseMetaData.getSchemas();
//
//			ResultSetMetaData metaData = resultSet.getMetaData();
//			int columnCount = metaData.getColumnCount();
//			List<String> cols = new ArrayList<>();
//			for (int i = 1; i <= columnCount; i++) {
//				String columnName = metaData.getColumnName(i);
//				System.out.println("Col => " + columnName);
//				cols.add(columnName);
//			}
//			
//			while(resultSet.next()) {
//				cols.forEach(colNme -> {
//					String value = null;
//					try {
//						value = resultSet.getString(colNme);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					System.out.println("Col = " + colNme + " , value => " + value);
//				});
//			}
//			
//			resultSet.last();
//			int count = resultSet.getRow();
//			System.out.println("count => " + count);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}

	private void connectUsingDriver(DataSource dataSource, ConnectionInfo connectionInfo, String dbType) {
		try {
			String driver = DBUtil.getConnectionDriver(dbType);
			Class.forName(driver);
			String username = connectionInfo.getUsername();
			String password = connectionInfo.getPassword();
			String connectionURL = connectionInfo.getConnectionURL();
			Connection connection = null;
			if (StringUtils.isAnyBlank(username, password)) {
				connection = DriverManager.getConnection(connectionURL);
			} else {
				connection = DriverManager.getConnection(connectionURL, username, password);
			}
			if (connection != null) {
				dataSource.setStatus("Active");
				getTableMetadata(connection, dataSource);
				connection.close();
			}
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
			System.out.println("tableName = " + tableName);
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
			getSampleData(connection, dataSource, sourceTable);
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
				+ "' AND TABLE_NAME = '" + sourceTable.getName() + "' ORDER BY ORDINAL_POSITION";
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

	private void getSampleData(Connection connection, DataSource dataSource, SourceTable sourceTable)
			throws SQLException {
		String sql = "SELECT * FROM " + dataSource.getName() + "." + sourceTable.getName() + " LIMIT 10;";
		ResultSet resultSet = getResultSet(connection, sql);
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
