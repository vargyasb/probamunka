package com.gbsolutions.probamunka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gbsolutions.probamunka.dto.PositionDTO;
import com.gbsolutions.probamunka.exception.ClientException;
import com.gbsolutions.probamunka.exception.PositionException;
import com.gbsolutions.probamunka.model.Position;
import com.gbsolutions.probamunka.repository.ClientRepository;
import com.gbsolutions.probamunka.repository.PositionRepository;

@Service
public class PositionService {
	
	@Autowired
	private PositionRepository positionRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	public String save(PositionDTO positionDTO) throws PositionException, ClientException{
		Position positionEntity = new Position();
		
		if (positionDTO.getPosition() == null || positionDTO.getLocation() == null) {
			throw new PositionException("Fields cannot be null");
		}
		if (clientRepository.findById(positionDTO.getClientID()) != null) {
			positionEntity.setClient(clientRepository.getOne(positionDTO.getClientID()));
		} else {
			throw new ClientException("Client not found!");
		}
		if (positionDTO.getPosition().length() <= 50) {
			positionEntity.setPosition(positionDTO.getPosition());
		} else {
			throw new PositionException("Positions should be under 50 characters!");
		}
		if (positionDTO.getLocation().length() <= 50) {
			positionEntity.setLocation(positionDTO.getLocation());
		} else {
			throw new PositionException("Locations should be under 50 characters!");
		}
		
		positionEntity = positionRepository.save(positionEntity);
		
		String baseUrl = 
				ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		
		return baseUrl + "/positions/" + positionEntity.getId();
	}
}
