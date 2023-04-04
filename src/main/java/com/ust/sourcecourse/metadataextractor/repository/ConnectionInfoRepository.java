package com.ust.sourcecourse.metadataextractor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ust.sourcecourse.metadataextractor.entity.ConnectionInfo;

public interface ConnectionInfoRepository extends JpaRepository<ConnectionInfo, Long> {

}
