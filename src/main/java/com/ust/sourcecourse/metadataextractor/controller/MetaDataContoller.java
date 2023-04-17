package com.ust.sourcecourse.metadataextractor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ust.sourcecourse.metadataextractor.service.MetaDataService;

@RestController
@RequestMapping("/meta")
public class MetaDataContoller {
	
	@Autowired
	private MetaDataService metaDataService;

	@GetMapping
	public ResponseEntity<String> getMetaData() {
		metaDataService.getMetadata();
		return ResponseEntity.ok("Saved");
	}

}
