package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "GroupSource")
public class GroupSource {

	@Id
	@Column(name = "uid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	@ElementCollection	
	private List<String>name = new ArrayList<>();
	@Column(name="description")
	private String description ;
	@ElementCollection
	private List<String> tags = new ArrayList<>();	
	@Column(name = "createdBy")
	private String createdBy;
	@Column(name = "created_timestamp")
	private LocalDateTime createdTimestamp;
	@Column(name = "modifiedBy")
	private String modifiedBy;
	@Column(name = "modified_timestamp")
	private LocalDateTime modifiedTimestamp;


}
