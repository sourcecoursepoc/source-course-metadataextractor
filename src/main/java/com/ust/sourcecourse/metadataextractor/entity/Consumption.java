package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "Consumption")
public class Consumption {
	

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@ManyToOne
	@JoinColumn(name = "group_uids")
	private String columnUid;
	@Column(name = "count")
	private int count;
	@Column(name = "group_uids")
	private List<String> groupUids;
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;
	@Column(name = "modifiedBy")
	private String modifiedBy;
	@Column(name = "modified_timestamp")
	private LocalDateTime modifiedTimestamp;

}
