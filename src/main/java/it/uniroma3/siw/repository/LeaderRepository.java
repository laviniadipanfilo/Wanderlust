package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Leader;

public interface LeaderRepository extends CrudRepository<Leader, Long> {

	public boolean existsByNameAndSurname(String name, String surname);

	
}