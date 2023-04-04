package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.sourcecourse.metadataextractor.entity.SourceTable;

public interface SourceTableRepository extends JpaRepository<SourceTable, Long> {

}
