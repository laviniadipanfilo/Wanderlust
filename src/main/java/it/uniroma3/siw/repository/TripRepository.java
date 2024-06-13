package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Trip;

public interface TripRepository extends CrudRepository<Trip, Long> {

	public boolean existsByNameAndId(String name, Long id);
	
	public boolean existsByName(String name);

	
}