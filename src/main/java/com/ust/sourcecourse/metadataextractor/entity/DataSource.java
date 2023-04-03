package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "DataSources")
public class DataSource {
	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@Column(name = "name")
	private String name;
	@Column(name="description")
	private String description ;
	@Column(name = "status")
	private String status;
	@Column(name = "region")
	private String region;
	@Column(name = "totalTables")
	private int totalTables;
	@Column(name = "size")
	private long size;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;
	@Column(name = "modified_by")
	private String modifiedBy;
	@Column(name = "modified_timestamp")
	private LocalDateTime modifiedTimestamp;
	@OneToMany(mappedBy = "dataSource", cascade = CascadeType.ALL)
	private List<ConnectionInfo> connections;
	@OneToMany(mappedBy = "dataSource", cascade = CascadeType.ALL)
	private List<SourceTable> tables;

}
