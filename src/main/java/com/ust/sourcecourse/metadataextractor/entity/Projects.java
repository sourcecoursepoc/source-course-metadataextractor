package com.ust.sourcecourse.metadataextractor.entity;

import java.sql.Date;


import org.springframework.data.annotation.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;



@Entity
@Data
@Table(name="Projects")
public class Projects {
	

	    @Id
	    @Column(name = "uid")
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private String uid;
	    
	    @Column(name = "name", nullable = false)
	    private String name;
	    
	    @Column(name = "description")
	    private String description;
	    
	    @Column(name = "createdBy")
	    private String createdBy;
	    
	    @Column(name = "created_timestamp")
//	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdTimestamp;
	    
	    @Column(name = "modifiedBy")
	    private String modifiedBy;
	    
	    @Column(name = "modified_timestamp")
//	    @Temporal(TemporalType.TIMESTAMP)
	    private Date modifiedTimestamp;
	    

}

