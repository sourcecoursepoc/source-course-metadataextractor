package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.List;

public class DataSource {

	private String uid;
	private String name;
	private String description;
	private String status;
	private String region;
	private int totalTables;
	private long size;
	private String createdBy;
	private LocalDateTime createdTimestamp;
	private String modifiedBy;
	private LocalDateTime modifiedTimestamp;
	private List<ConnectionInfo> connections;
	private List<SourceTable> tables;

		
	
	public DataSource() {
		super();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getTotalTables() {
		return totalTables;
	}

	public void setTotalTables(int totalTables) {
		this.totalTables = totalTables;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(LocalDateTime modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public List<ConnectionInfo> getConnections() {
		return connections;
	}

	public void setConnections(List<ConnectionInfo> connections) {
		this.connections = connections;
	}

	public List<SourceTable> getTables() {
		return tables;
	}

	public void setTables(List<SourceTable> tables) {
		this.tables = tables;
	}

}
