package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;

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
@Table(name = "GroupColumn")
public class GroupColumn {

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@ManyToOne
	@JoinColumn(name = "group_uid", nullable = false)
	private String groupUid;
	@Column(name = "name")
	private String name;
	@Column(name = "notes")
	private String notes;
	@Column(name = "type")
	private String type;
	@Column(name = "isPrimary")
	private boolean isPrimary;
	@Column(name = "uniqueKey")
	private String uniqueKey;
	@Column(name = "nullable")
	private boolean isNullable;
	@Column(name = "defaultValue")
	private String defaultValue;
	@Column(name = "prefix")
	private String prefix;
	@Column(name = "suffix")
	private String suffix;
	@ManyToOne
	@JoinColumn(name = "source_column_uid", nullable = false)
	private String sourceColumnUid;
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;
	@Column(name = "modifiedBy")
	private String modifiedBy;
	@Column(name = "modified_timestamp")
	private LocalDateTime modifiedTimestamp;

}
