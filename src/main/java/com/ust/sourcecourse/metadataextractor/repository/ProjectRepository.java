package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.sourcecourse.metadataextractor.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
