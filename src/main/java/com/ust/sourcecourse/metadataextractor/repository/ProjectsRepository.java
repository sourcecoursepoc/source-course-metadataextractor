package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.sourcecourse.metadataextractor.entity.Projects;

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

}
