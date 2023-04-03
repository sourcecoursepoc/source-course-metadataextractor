package com.ust.sourcecourse.metadataextractor.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SourceTable {
	
	private String uid;
	  private String name;
	  private String description;
	  private String datasourceUid;
	  private long rowCount;
	  private long size;
	  private LocalDate minDate;
	  private LocalDate maxDate;
	  private long yoyCount;
	  private long momCount;
	  private List<SourceColumn> columns;
	  private String createdBy;
	  private LocalDateTime createdTimestamp;
	  private String modifiedBy;
	  private LocalDateTime modifiedTimestamp;

}
