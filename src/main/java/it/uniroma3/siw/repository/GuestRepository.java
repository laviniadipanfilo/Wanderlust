package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Guest;

public interface GuestRepository extends CrudRepository<Guest, Long> {

}