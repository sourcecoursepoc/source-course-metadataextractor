package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Consumption {

	private String uid;
	private String columnUid;
	private int count;
	private List<String> groupUids;
	private String createdBy;
	private LocalDateTime createdTimestamp;
	private String modifiedBy;
	private LocalDateTime modifiedTimestamp;

	public Consumption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getColumnUid() {
		return columnUid;
	}

	public void setColumnUid(String columnUid) {
		this.columnUid = columnUid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getGroupUids() {
		return groupUids;
	}

	public void setGroupUids(List<String> groupUids) {
		this.groupUids = groupUids;
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

}
