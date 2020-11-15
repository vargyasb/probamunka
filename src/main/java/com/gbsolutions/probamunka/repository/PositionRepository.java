package com.gbsolutions.probamunka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gbsolutions.probamunka.model.Position;

public interface PositionRepository extends JpaRepository<Position, String>{
	List<Position> findByTitle(String title);
	List<Position> findByLocation(String location);
	List<Position> findByTitleAndLocation(String title, String location);
}
