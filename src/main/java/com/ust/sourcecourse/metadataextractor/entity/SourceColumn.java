package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.List;

public class SourceColumn {
	
	private String uid;
	  private String name;
	  private String description;
	  private String tableUid;
	  private String type;
	  private boolean isPrimary;
	  private boolean isUnique;
	  private boolean isNullable;
	  private String defaultValue;
	  private String tags;
	  private int reportUid;
	  private String createdBy;
	  private LocalDateTime createdTimestamp;
	  private String modifiedBy;
	  private LocalDateTime modifiedTimestamp;
	  private List<Consumption> consumptions;
	  private List<DataQuality> dataQualities;
	  
	  
	public SourceColumn() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getTableUid() {
		return tableUid;
	}
	public void setTableUid(String tableUid) {
		this.tableUid = tableUid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPrimary() {
		return isPrimary;
	}
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	public boolean isUnique() {
		return isUnique;
	}
	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}
	public boolean isNullable() {
		return isNullable;
	}
	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getReportUid() {
		return reportUid;
	}
	public void setReportUid(int reportUid) {
		this.reportUid = reportUid;
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
	public List<Consumption> getConsumptions() {
		return consumptions;
	}
	public void setConsumptions(List<Consumption> consumptions) {
		this.consumptions = consumptions;
	}
	public List<DataQuality> getDataQualities() {
		return dataQualities;
	}
	public void setDataQualities(List<DataQuality> dataQualities) {
		this.dataQualities = dataQualities;
	}
	  
	  

}
