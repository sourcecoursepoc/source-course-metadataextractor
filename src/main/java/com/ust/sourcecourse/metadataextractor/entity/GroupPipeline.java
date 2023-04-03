package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;

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
@Table(name = "Group_Pipeline")
public class GroupPipeline {

	@Id
	@Column(name = "uid", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@ManyToOne
	@JoinColumn(name = "group_uid")
	private String groupUid;
	@Column(name = "exportType")
	private String exportType;
	@Column(name = "loadType")
	private String loadType;
	@Column(name = "recurrance")
	private String recurrence;
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;
	@Column(name = "modifiedBy")
	private String modifiedBy;
	@Column(name = "modified_timestamp")
	private LocalDateTime modifiedTimestamp;

}
