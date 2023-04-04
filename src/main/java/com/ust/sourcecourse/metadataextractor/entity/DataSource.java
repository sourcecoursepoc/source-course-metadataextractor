package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "data_source")
public class DataSource {

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	private String status;

	@Column(name = "region")
	private String region;

	@Column(name = "totalTables")
	private int totalTables;

	@Column(name = "size")
	private long size;

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

	@OneToOne(mappedBy = "dataSource")
	private ConnectionInfo connectionInfo;

	@OneToMany(mappedBy = "dataSource")
	private List<SourceTable> sourceTables;

}
