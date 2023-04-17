package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_column")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	@CreationTimestamp
	private LocalDateTime createdTimestamp;

	@Column(name = "modified_by")
	@LastModifiedBy
	private String modifiedBy;

	@Column(name = "modified_timestamp")
	@UpdateTimestamp
	private LocalDateTime modifiedTimestamp;

}
