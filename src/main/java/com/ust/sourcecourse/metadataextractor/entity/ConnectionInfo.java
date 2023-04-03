package com.ust.sourcecourse.metadataextractor.entity;

public class ConnectionInfo {
	
		  private String uid;
		  private String datasourceUid;
		  private String connectionURL;
		  private String username;
		  private String password;
		  
		  
		public ConnectionInfo() {
			super();
			
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getDatasourceUid() {
			return datasourceUid;
		}
		public void setDatasourceUid(String datasourceUid) {
			this.datasourceUid = datasourceUid;
		}
		public String getConnectionURL() {
			return connectionURL;
		}
		public void setConnectionURL(String connectionURL) {
			this.connectionURL = connectionURL;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		  
		  

}
