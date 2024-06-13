package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Leader;
import it.uniroma3.siw.repository.LeaderRepository;

@Service
public class LeaderService {
	
	@Autowired LeaderRepository leaderRepository;

	public Leader findById(Long id) {
        return leaderRepository.findById(id).orElse(null);
    }

    public Iterable<Leader> findAll() {
        return leaderRepository.findAll();
    }
    
    public Leader save(Leader l) {
        return leaderRepository.save(l);
    }
    
    public String delete(Long id) {
    	this.leaderRepository.deleteById(id);
    	return "leader cancellato";
    }
	
}