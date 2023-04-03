package com.ust.sourcecourse.metadataextractor.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table()
public class ConnectionInfo {
	
		  @Id
		  @Column(name = "uid")
		  @GeneratedValue(strategy = GenerationType.IDENTITY)
		  private String uid;
		  /**
		   * 
		   */
		  @ManyToOne
		  @JoinColumn(name = "datasource_uid", referencedColumnName = "uid")
		  private String datasourceUid;
		  @Column(name = "connectionURL")
		  private String connectionURL;
		  @Column(name = "username")
		  private String username;
		  @Column(name="password")
		  private String password;
		  
		  
		
		  
		  

}
