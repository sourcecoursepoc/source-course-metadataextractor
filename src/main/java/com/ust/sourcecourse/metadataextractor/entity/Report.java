package com.ust.sourcecourse.metadataextractor.entity;

import java.sql.Date;

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
@Table(name = "Report")
public class Report {

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@ManyToOne
	@JoinColumn(name = "consumption_uid", referencedColumnName = "uid")
	private String consumptionUid;
	@ManyToOne
	@JoinColumn(name = "dataQuality_uid", referencedColumnName = "uid")
	private String dataQualityUid;
	@Column(name = "createdBy")

	private String createdBy;
	@Column(name = "created_timestamp")
	private Date createdTimestamp;
	@Column(name = "modifiedBy")
	private String modifiedBy;
	@Column(name = "modified_timestamp")
	private Date modifiedTimestamp;

}
