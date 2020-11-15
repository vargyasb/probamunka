package com.gbsolutions.probamunka.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "positions")
public class Position implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String title;
	
	private String location;
	
	private String url;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + 
				", title=" + title + 
				", location=" + location + 
				", url=" + url + 
				", client=" + client + "]";
	}

	

	
	
	
}
