package com.gbsolutions.probamunka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gbsolutions.probamunka.model.Client;

public interface ClientRepository extends JpaRepository<Client, String>{
	Client findByEmail(String email);
}
