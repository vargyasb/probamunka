package com.gbsolutions.probamunka.controller;

import com.gbsolutions.probamunka.service.ClientService;
import com.gbsolutions.probamunka.dto.ClientDTO;
import com.gbsolutions.probamunka.exception.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {
	private static final Logger log = LoggerFactory.getLogger(ClientController.class);
	
	@Autowired
	private ClientService clientService;

	@PostMapping
	public ResponseEntity<String> createClient(@RequestBody ClientDTO client) {
		try {
			return ResponseEntity.ok(clientService.save(client));
		} catch (ClientException e) {
			log.error("client ex: ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("ex: ", e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
