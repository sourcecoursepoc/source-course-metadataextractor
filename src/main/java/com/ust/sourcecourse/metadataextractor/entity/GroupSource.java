package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.List;

public class GroupSource {

	private String uid;
	private String name;
	private String description;
	private String tags;
	private String createdBy;
	private LocalDateTime createdTimestamp;
	private String modifiedBy;
	private LocalDateTime modifiedTimestamp;
	private List<SourceColumn> columns;
	private List<GroupColumn> groupColumns;
	private List<GroupPipeline> pipelines;

	public GroupSource() {
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
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

	public List<SourceColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<SourceColumn> columns) {
		this.columns = columns;
	}

	public List<GroupColumn> getGroupColumns() {
		return groupColumns;
	}

	public void setGroupColumns(List<GroupColumn> groupColumns) {
		this.groupColumns = groupColumns;
	}

	public List<GroupPipeline> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<GroupPipeline> pipelines) {
		this.pipelines = pipelines;
	}

}
