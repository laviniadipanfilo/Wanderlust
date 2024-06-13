package it.uniroma3.siw.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Stop;
import it.uniroma3.siw.model.Trip;
import it.uniroma3.siw.repository.StopRepository;
import it.uniroma3.siw.repository.TripRepository;
import it.uniroma3.siw.service.StopService;
import it.uniroma3.siw.service.TripService;

@Controller
public class StopController {
	
	@Autowired StopService stopService;
	@Autowired StopRepository stopRepository;
	@Autowired TripService tripService;
	@Autowired TripRepository tripRepository;

	@GetMapping("/stop/{idStop}")
	public String showStop(@PathVariable("idStop") Long idStop, Model model) {
		model.addAttribute("stop", this.stopService.findById(idStop));
		return "stop.html";
	}
	
	@GetMapping("/stops")
	public String showStops(Model model) {
		model.addAttribute("stops", this.stopService.findAll());
		return "stops.html";
	}
	
	@GetMapping("/trips/stop/{idStop}")
	public String showTripsWithStop(@PathVariable("idStop") Long idStop, Model model) {
		Stop stop = this.stopRepository.findById(idStop).orElse(null);
	    if (idStop == null) {
	    	return "stop.html";
	    }
	    else {
		    List<Trip> viaggiCheHannoQuellaTappa =  new LinkedList();
		    Iterable<Trip> allTrips = this.tripRepository.findAll();
		    for(Trip i: allTrips) {
		    	
		    	if(i.getLocation().equals(stop.getLocation()))
		    		viaggiCheHannoQuellaTappa.add(i);
		    	
		    	List<Stop> tappe = i.getStops();
		    	for(Stop j: tappe) {
		    		if(j.getLocation().equals(stop.getLocation()))
		    			viaggiCheHannoQuellaTappa.add(i);
		    	}
		    	
		    
		   
		    }
		    
		    model.addAttribute("stop", stop);
		    model.addAttribute("tripsWithStop", viaggiCheHannoQuellaTappa);
		    
		    return "tripsWithStop.html";
	    }
	}
	
//	@PostMapping("/deleteStop/{idTrip}/{idStop}")
//	public String cancellaIngrediente(@PathVariable("idTrip") Long idTrip, @PathVariable("idStop") Long idStop, Model model) {
//	    Trip trip = tripRepository.findById(idTrip).orElse(null);
//	    Stop daCancellare = null;
//	    for (Stop i : trip.getStops()) {
//	        if (i.getId().equals(idStop)) {
//	            daCancellare = i;
//	            break;
//	        }
//	    }
//
//	    if (daCancellare != null) {
//	        trip.getStops().remove(daCancellare);
//	        stopRepository.delete(daCancellare);
//	        tripRepository.save(trip);
//	    } else {
//	        model.addAttribute("messaggioErrore", "Tappa non associato all viaggio");
//	        return "errore.html";
//	    }
//
//	    model.addAttribute("trip", trip);
//	    model.addAttribute("trips", this.tripRepository.findAll());
//	    model.addAttribute("stops", (List<Stop>) stopRepository.findAll());
//	    return "trips.html";
//	}
	
}
