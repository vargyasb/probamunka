package com.gbsolutions.probamunka.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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
	@Autowired
	private ModelMapper modelMapper;
	
	public String save(PositionDTO positionDTO) throws PositionException, ClientException{
		Position positionEntity = new Position();
		
		if (positionDTO.getTitle() == null || positionDTO.getLocation() == null) {
			throw new PositionException("Fields cannot be null");
		}
		if (clientRepository.findById(positionDTO.getClientID()) != null) {
			positionEntity.setClient(clientRepository.getOne(positionDTO.getClientID()));
		} else {
			throw new ClientException("Client not found!");
		}
		if (positionDTO.getTitle().length() <= 50) {
			positionEntity.setTitle(positionDTO.getTitle());
		} else {
			throw new PositionException("Positions should be under 50 characters!");
		}
		if (positionDTO.getLocation().length() <= 50) {
			positionEntity.setLocation(positionDTO.getLocation());
		} else {
			throw new PositionException("Locations should be under 50 characters!");
		}
		
		positionEntity.setId(UUID.randomUUID().toString());
		
		String baseUrl = 
				ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		
		positionEntity.setUrl(baseUrl + "/positions/" + positionEntity.getId());
		
		positionEntity = positionRepository.save(positionEntity);
		
		return positionEntity.getUrl();
	}
	
	
	public List<PositionDTO> getPositions(String title, String location) throws PositionException {
		List<Position> positions = new ArrayList<>();
		List<PositionDTO> positionDTOS = new ArrayList<>();
		
		if (title != null && location != null) {
			if (title.length() <= 50 && location.length() <= 50) {
				positions = positionRepository.findByTitleAndLocation(title, location);
				positionDTOS = mapAll(positions, PositionDTO.class);
				positionDTOS.addAll(getPositionsFromGithub(title, location));
			}
		} else if (title != null) {
			if (title.length() <= 50) {
				positions = positionRepository.findByTitle(title);
				positionDTOS = mapAll(positions, PositionDTO.class);
				positionDTOS.addAll(getPositionsFromGithub(title, location));
			}
		} else if (location != null) {
			if (location.length() <= 50) {
				positions = positionRepository.findByLocation(location);
				positionDTOS = mapAll(positions, PositionDTO.class);
				positionDTOS.addAll(getPositionsFromGithub(title, location));
			}
		} else {
			positions = positionRepository.findAll();
			positionDTOS = mapAll(positions, PositionDTO.class);
			positionDTOS.addAll(getPositionsFromGithub(title, location));
		}
		return positionDTOS;
	}
	
	public PositionDTO getPositionById(String id) throws PositionException {
		if (positionRepository.findById(id) != null) {
			Position positionEntity = positionRepository.getOne(id);
			PositionDTO positionDTO = modelMapper.map(positionEntity, PositionDTO.class);

			return positionDTO;
		} else {
			throw new PositionException("Position not found!");
		}
	}
	
	private List<PositionDTO> getPositionsFromGithub(String title, String location) {
		RestTemplate restTemplate = new RestTemplate();
		String baseURL = "https://jobs.github.com/positions.json?";
		if (title != null && location!= null) {
			baseURL += "title=" + title + "&location=" + location;
		} else if (title != null) {
			baseURL += "title=" + title;
		} else if (location != null) {
			baseURL += "location=" + location;
		}
		System.out.println(baseURL);
		
		ResponseEntity<PositionDTO[]> response =
				  restTemplate.getForEntity(
				  baseURL,
				  PositionDTO[].class);
				PositionDTO[] positions = response.getBody();
		
		return Arrays.asList(positions);
	}
	
	//Entity lista DTO listava konvertalasa - UTILS
	public <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }
	
	public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }
	
	public <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
