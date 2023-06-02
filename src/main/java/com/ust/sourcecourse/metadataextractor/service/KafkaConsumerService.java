package com.ust.sourcecourse.metadataextractor.service;

//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import com.ust.sourcecourse.metadataextractor.constant.AppConstant;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Service
//public class KafkaConsumerService {
//@Autowired
//MetaDataService metaDataService;
//    @KafkaListener(topics = AppConstant.KAFKA_METADATA_EVENT_TOPIC,
//            groupId = AppConstant.KAFKA_GROUP_ID)
//    public void consume(String message){
//        log.info(String.format("Message received -> %s", message));
//        metaDataService.getMetadata(Long.valueOf(message));
//    }
//}
