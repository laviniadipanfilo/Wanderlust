package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Stop;
import it.uniroma3.siw.repository.StopRepository;

@Service
public class StopService {

	@Autowired StopRepository stopRepository;

	public Stop findById(Long id) {
        return stopRepository.findById(id).orElse(null);
    }

    public Iterable<Stop> findAll() {
        return stopRepository.findAll();
    }
    
    public Stop save(Stop s) {
        return stopRepository.save(s);
    }
    
}
