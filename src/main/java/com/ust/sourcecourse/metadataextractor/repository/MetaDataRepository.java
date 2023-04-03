package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ust.sourcecourse.metadataextractor.entity.Projects;



	@Repository
	public interface MetaDataRepository extends JpaRepository<Projects, Long> {
	}
	
	
