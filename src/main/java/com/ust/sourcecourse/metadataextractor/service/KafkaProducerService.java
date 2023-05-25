package com.ust.sourcecourse.metadataextractor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ust.sourcecourse.metadataextractor.constant.AppConstant;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducerService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@PostConstruct
	public String producer() {
		String message = "test message";
		kafkaTemplate.send(AppConstant.KAFKA_PIPELINE_EVENT_TOPIC, message);
		log.info(String.format("Message received -> %s", message));
		return "Message sent to pipeline extractor";
	}

}