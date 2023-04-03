package com.ust.sourcecourse.metadataextractor.entity;

import java.sql.Date;

import org.hibernate.annotations.Table;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
//@Table(name = "Projects")

public class Projects {
	

	    @Id
	    @Column(name = "uid")
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

