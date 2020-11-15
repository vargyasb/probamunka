package com.gbsolutions.probamunka.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping
	public ResponseEntity<List<PositionDTO>> listPositions(@RequestParam(required = false) String title,
			@RequestParam(required = false) String location) {
		try {
			return ResponseEntity.ok(positionService.getPositions(title, location));
		} catch (PositionException e) {
			log.error("client/position ex: ", e);
			
			// nem engedi kiiratni az exception-t
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("exception ex: ", e);
			//nem engedi kiiratni az exception-t
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/{id}")
	  public ResponseEntity<PositionDTO> listPositionById(@PathVariable(value = "id") String positionId) {
	    try {
	    	return ResponseEntity.ok(positionService.getPositionById(positionId));
	    } catch (PositionException e) {
	    	log.error("position ex:", e);
	    	
	    	//nem engedi kiiratni az exception-t
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	log.error("exception ex: ", e);
	    	
	    	//nem engedi kiiratni az exception-t
	    	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	  }

}
