package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.sourcecourse.metadataextractor.entity.SourceColumn;

public interface SourceColumnRepository extends JpaRepository<SourceColumn, Long> {

}
