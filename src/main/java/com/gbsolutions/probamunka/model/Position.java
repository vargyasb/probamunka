package com.gbsolutions.probamunka.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String position;
	
	private String location;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + 
				", position=" + position + 
				", location=" + location + 
				"," + client + "]";
	}

	
	
	
}
