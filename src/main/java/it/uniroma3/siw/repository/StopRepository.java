package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Stop;

public interface StopRepository extends CrudRepository<Stop, Long> {

	public boolean existsByLocationAndId(String location, Long id);
	
	public boolean existsByLocation(String location);
	
}
