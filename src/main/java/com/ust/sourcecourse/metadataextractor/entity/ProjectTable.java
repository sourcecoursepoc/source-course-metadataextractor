package com.ust.sourcecourse.metadataextractor.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name="ProjectTable")
public class ProjectTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@ManyToMany
	private Projects project;
	@Column(name="table")
	private SourceTable table;
	@Column(name="createdBy")
	private String createdBy;
	@Column(name="createdTimestamp")
	private Date createdTimestamp;
	@Column(name="modifiedBy")
	private String modifiedBy;
	@Column(name="modifiedTimestamp")
	private Date modifiedTimestamp;


}
