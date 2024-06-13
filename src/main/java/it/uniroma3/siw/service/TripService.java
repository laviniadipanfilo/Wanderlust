package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Trip;
import it.uniroma3.siw.repository.TripRepository;

@Service
public class TripService {

	@Autowired TripRepository tripRepository;
	
	public Trip findById(Long id) {
        return tripRepository.findById(id).orElse(null);
    }

    public Iterable<Trip> findAll() {
        return tripRepository.findAll();
    }
    
    public Trip save(Trip t) {
        return tripRepository.save(t);
    }
    
    public String delete(Long id) {
    	this.tripRepository.deleteById(id);
    	return "viaggio cancellato";
    }
    
    public void saveImage(MultipartFile imageFile) {
    	String folder = "/photos/";
    }

}