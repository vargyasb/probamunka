package com.gbsolutions.probamunka.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbsolutions.probamunka.dto.ClientDTO;
import com.gbsolutions.probamunka.exception.ClientException;
import com.gbsolutions.probamunka.repository.ClientRepository;
import com.gbsolutions.probamunka.model.Client;

@Service
public class ClientService {
	
	private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	
	@Autowired
	private ClientRepository clientRepository;

	public String save(ClientDTO clientDTO) throws ClientException {
		Client clientEntity = new Client();
		if(clientDTO.getEmail() == null || clientDTO.getName() == null) {
			throw new ClientException("Fields cannot be null!");
		}
		if (clientDTO.getName().length() <= 100) {
			clientEntity.setName(clientDTO.getName());
		} else {
			throw new ClientException("Name should be under 100 characters!");
		}
		if (clientDTO.getEmail().matches(EMAIL_PATTERN)) {
			if (clientRepository.findByEmail(clientDTO.getEmail()) == null) {
				clientEntity.setEmail(clientDTO.getEmail());
			} else {
				throw new ClientException("Email is already in use.");
			}
		} else {
			throw new ClientException("Email does not meet the criteria.");
		}
		clientEntity.setId(UUID.randomUUID().toString());
		
		clientRepository.save(clientEntity);
			
		return clientEntity.getId();
	}
	
	
}
