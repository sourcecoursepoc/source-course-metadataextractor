package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.sourcecourse.metadataextractor.entity.DataSource;

public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

}
