package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "source_column")
public class SourceColumn {
	
	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@ManyToOne
	@JoinColumn(name = "source_table_uid", referencedColumnName = "uid")
	private SourceTable sourceTable;

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

	@ElementCollection
	private List<String> tags = new ArrayList<>();

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
	
	@OneToOne(mappedBy = "sourceColumn")
	private Report report;
	
	@OneToMany(mappedBy = "sourceColumn")
	private List<GroupColumn> groupColumns;
}
