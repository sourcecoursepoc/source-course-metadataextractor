package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "Source_Tables")
public class SourceTable {

	@Id
	@Column(name = "uid", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	@ManyToOne
	@JoinColumn(name = "datasource_uid")
	private String datasourceUid;
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
	private List<String> tags= new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "datasource_uid", referencedColumnName = "uid")
	private DataSource dataSource;
	@ManyToOne
	@JoinColumn(name = "sourcecolumn_uid", referencedColumnName = "uid")
	private List<SourceColumn> columns;
	@Column(name="createdBy")
	private String createdBy;
	@Column(name = "created_timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdTimestamp;
	@Column(name = "modifiedBy", length = 255)
	private String modifiedBy;
	@Column(name = "modified_timestamp", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private LocalDateTime modifiedTimestamp;

}
