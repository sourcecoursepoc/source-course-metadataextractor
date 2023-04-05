package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "source_table")
@Builder
public class SourceTable {

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@ManyToOne
	@JoinColumn(name = "datasource_uid", referencedColumnName = "uid")
	private DataSource dataSource;

	@Column(name = "rowCount")
	private long rowCount;

	@Column(name = "size")
	private long size;

	@Column(name = "minDate")
	private LocalDate minDate;

	@Column(name = "maxDate")
	private LocalDate maxDate;

	@Column(name = "yoyCount")
	private long yoyCount;

	@Column(name = "momCount")
	private long momCount;

	@ElementCollection
	private List<String> tags = new ArrayList<>();

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

	@OneToMany(mappedBy = "sourceTable")
	private List<SourceColumn> sourceColumns;

	@OneToMany(mappedBy = "sourceTable")
	private List<ProjectTable> projectTables;

}
