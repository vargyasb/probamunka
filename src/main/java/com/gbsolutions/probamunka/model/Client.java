package com.gbsolutions.probamunka.model;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {

	@Id
	@Column(name = "id", length=50)
	private String id;
	
    private String name;

    private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + 
				", name=" + name + 
				", email=" + email + "]";
	}

	
   
    
}
