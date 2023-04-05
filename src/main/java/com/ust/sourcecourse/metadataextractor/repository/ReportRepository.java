package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.sourcecourse.metadataextractor.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
