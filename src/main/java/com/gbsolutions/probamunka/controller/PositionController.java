package com.gbsolutions.probamunka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbsolutions.probamunka.dto.PositionDTO;
import com.gbsolutions.probamunka.exception.ClientException;
import com.gbsolutions.probamunka.exception.PositionException;
import com.gbsolutions.probamunka.service.PositionService;

@RestController
@RequestMapping("/positions")
public class PositionController {
	private static final Logger log = LoggerFactory.getLogger(PositionController.class);
	
	@Autowired
	private PositionService positionService;
	
	@PostMapping
	public ResponseEntity<String> createPosition(@RequestBody PositionDTO position) {
		try {
			return ResponseEntity.ok(positionService.save(position));
		} catch (ClientException | PositionException e) {
			log.error("client/position ex: ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("exception ex: ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
