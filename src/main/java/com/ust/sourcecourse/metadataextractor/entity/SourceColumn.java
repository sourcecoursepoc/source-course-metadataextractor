package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Source_Columns")
public class SourceColumn {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid",nullable =false)
	private String uid;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description")
	private String description;
	@ManyToOne
	@JoinColumn(name = "table_uid")
	private String tableUid;
	@Column(name = "type")
	private String type;
	@Column(name = "isPrimary")
	private boolean isPrimary;
	@Column(name = "isUnique")
	private boolean isUnique;
	@Column(name = "isNullable")
	private boolean isNullable;
	@Column(name = "defaultValue")
	private String defaultValue;
	@Column(name = "tag")
	private List<String> tags;
	@Column(name = "report_uid")
	private int reportUid;
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;
	@Column(name = "modifiedBy")
	private String modifiedBy;
	@Column(name = "modified_timestamp")
	private LocalDateTime modifiedTimestamp;
}
