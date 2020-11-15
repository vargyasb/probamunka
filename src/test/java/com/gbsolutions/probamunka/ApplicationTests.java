package com.gbsolutions.probamunka;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.gbsolutions.probamunka.dto.ClientDTO;
import com.gbsolutions.probamunka.dto.PositionDTO;
import com.gbsolutions.probamunka.model.Client;
import com.gbsolutions.probamunka.model.Position;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;
	
	private Client client;
	private Position position;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	@Order(1)
	public void contextLoads() {
	}
	
	@Test 
	@Order(2)
	public void testCreateClient() {
		client = new Client();
		client.setEmail("test@gmail.com");
		client.setName("TestUser");

		ResponseEntity<Client> postResponse = restTemplate.postForEntity(getRootUrl() + "/clients", client, Client.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	@Order(3)
	public void testCreatePosition() {
		position = new Position();
		position.setLocation("London");
		position.setTitle("Developer");

		ResponseEntity<Position> postResponse = restTemplate.postForEntity(getRootUrl() + "/positions", position, Position.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	@Order(4)
	public void testGetPositionById() {
		position = restTemplate.getForObject(getRootUrl() + "/positions/" + position.getId(), Position.class);
		System.out.println(position.getTitle());
		Assert.assertNotNull(position);
	}
	
	@Test
	@Order(5)
	public void testGetAllPositions() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/positions",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}
}
