package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "group_column")
public class GroupColumn {

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@ManyToOne
	@JoinColumn(name = "project_group_uid", referencedColumnName = "uid")
	private ProjectGroup projectGroup;

	@Column(name = "name")
	private String name;

	@Column(name = "notes")
	private String notes;

	@Column(name = "type")
	private String type;

	@Column(name = "isPrimary")
	private boolean isPrimary;

	@Column(name = "uniqueKey")
	private boolean uniqueKey;

	@Column(name = "nullable")
	private boolean isNullable;

	@Column(name = "defaultValue")
	private String defaultValue;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "suffix")
	private String suffix;

	@OneToOne
	@JoinColumn(name = "source_column_uid", nullable = false)
	private SourceColumn sourceColumn;

	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;

	@Column(name = "created_timestamp")
	@CreatedDate
	private LocalDateTime createdTimestamp;

	@Column(name = "modified_by")
	@LastModifiedBy
	private String modifiedBy;

	@Column(name = "modified_timestamp")
	@LastModifiedDate
	private LocalDateTime modifiedTimestamp;

}
