package com.gbsolutions.probamunka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gbsolutions.probamunka.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>{
	
}
