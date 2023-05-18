package com.ust.sourcecourse.metadataextractor.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ust.sourcecourse.metadataextractor.constant.AppConstant;
import com.ust.sourcecourse.metadataextractor.service.MetaDataService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventListener {
	
	@Autowired
	private MetaDataService metaDataService;

	@KafkaListener(topics = AppConstant.KAFKA_DATASOURCE_EVENT_TOPIC, groupId = AppConstant.KAFKA_GROUP_ID)
	public void dataSourceConsumer(String message) {
		Long dataSourceUid = Long.valueOf(message);
		log.info(String.format("Received dataSourceUid -> %s", dataSourceUid));
		metaDataService.extractMetadataOfDB(dataSourceUid);
	}
}
