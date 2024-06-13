package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.TripValidator;
import it.uniroma3.siw.model.Leader;
import it.uniroma3.siw.model.Stop;
import it.uniroma3.siw.model.Trip;
import it.uniroma3.siw.repository.LeaderRepository;
import it.uniroma3.siw.repository.StopRepository;
import it.uniroma3.siw.repository.TripRepository;
import it.uniroma3.siw.service.LeaderService;
import it.uniroma3.siw.service.StopService;
import it.uniroma3.siw.service.TripService;

@Controller
public class TripController {
	
	@Autowired TripService tripService;
	@Autowired TripRepository tripRepository;
	@Autowired LeaderService leaderService;
	@Autowired LeaderRepository leaderRepository;
	@Autowired StopService stopService;
	@Autowired StopRepository stopRepository;
	@Autowired TripValidator tripValidator;
	
	@GetMapping("/trip/{idTrip}")
	public String showTrip(@PathVariable("idTrip") Long idTrip, Model model) {
		model.addAttribute("trip", this.tripService.findById(idTrip));
		return "trip.html";
	}
	
	@GetMapping("/trips")
	public String showTrips(Model model) {
		model.addAttribute("trips", this.tripService.findAll());
		return "trips.html";
	}
		
	@GetMapping("/formNewTrip")
	public String formNewTrip(Model model) {
		model.addAttribute("trip", new Trip());
		return "formNewTrip.html";
	}
	
	@PostMapping("/trip")
	public String newTrip(@ModelAttribute("trip") Trip trip, Model model) {
		if(!tripRepository.existsByName(trip.getName())) {
			this.tripService.save(trip);
			return "redirect:trip/"+trip.getId();
		}
		else {
			model.addAttribute("messaggioErrore", "Quel viaggio esiste già.... cambiagli nome o scegli quel viaggio!");
			return "formNewTrip.html";
		}
	}
	
	
	@PostMapping("/uploadImage")
	public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile ) {
		String ritorno = "start";
		
		this.tripService.saveImage(imageFile);
		return ritorno;
	}
	

//	@GetMapping("/deleteTrip")
//    public String deleteTrip(Model model) {
//    	model.addAttribute("trips", this.tripService.findAll());
//		return "deleteTrip.html";
//    }
	
	@PostMapping("/deleteTrip/{idTrip}")
	public String deleteTrip(@PathVariable("idTrip") Long idTrip, Model model) {
//		if (idTrip == null) {
//			model.addAttribute("trips", this.tripService.findAll());
//			return "trips.html";
//		}
		this.tripService.delete(idTrip);
    	model.addAttribute("trips", this.tripService.findAll());
		return "redirect:/trips";
	}
	
	@GetMapping("/addLeader/{idTrip}")
	public String showLeaders(@PathVariable("idTrip") Long idTrip, Model model) {
		model.addAttribute("leaders", this.leaderService.findAll());
		model.addAttribute("trip", tripRepository.findById(idTrip).get());
	    return "addLeader.html";
	}
	
	@GetMapping("/updateTrip/{idTrip}")
	public String formUpdateTrip(@PathVariable("idTrip") Long idTrip, Model model) {
		model.addAttribute("trip", this.tripService.findById(idTrip));
		return "updateTrip.html";
	}
	
	@PostMapping("/updateTrip/{idTrip}")
	public String setLeader(@PathVariable("idTrip") Long idTrip, @ModelAttribute("trip") Trip trip, Model model) {
		System.out.println("-----------------------------------------------------------------");
		if (!leaderRepository.existsByNameAndSurname(trip.getLeader().getName(), trip.getLeader().getSurname())) {
			System.out.println("--------------------------------------------------------------");
			this.leaderService.save(trip.getLeader());    
			return "redirect:updateTrip/"+trip.getId();
		} else {
			model.addAttribute("messaggioErrore", "Quest* leader c'è già");
			return "addLeader.html";
		}
	}
	
	@GetMapping("/setLeader/{idLeader}/{idTrip}")
	public String formLeader(@PathVariable("idLeader") Long idLeader, @PathVariable("idTrip") Long idTrip, Model model) {
		Leader leader = this.leaderRepository.findById(idLeader).get();
		Trip trip = this.tripRepository.findById(idTrip).get();
		trip.setLeader(leader);
		leader.getTrips().add(trip);
		this.tripRepository.save(trip);
		model.addAttribute("leader", leader);
		model.addAttribute("trip", trip);
		return "updateTrip.html";
	}
	
	@GetMapping("/trip/{idLeader}/{idTrip}")
	public String showTripByLeader(@PathVariable("idLeader") Long idLeader, @PathVariable("idTrip") Long idTrip, Model model) {
		model.addAttribute("leader", this.leaderService.findById(idLeader));
		if(tripRepository.findById(idTrip).get().getLeader().equals(leaderRepository.findById(idLeader).get())) {
			model.addAttribute("trip", this.tripService.findById(idTrip));
			return "trip.html";
		}
		return "trip.html";
	}
	
//	@GetMapping("/formAddStop/{idTrip}")
//	public String formNewStop(@PathVariable("idTrip") Long idTrip, Model model) {
//		Stop nuovaStop = new Stop();
//		model.addAttribute("stop", nuovaStop);
////		model.addAttribute("stop", new Stop());
//		model.addAttribute("trip", this.tripService.findById(idTrip));
//		nuovaStop.setTrip(this.tripRepository.findById(idTrip).get());
//		this.tripRepository.findById(idTrip).get().getStops().add(nuovaStop);
//		return "formAddStop.html";
//	}

	@GetMapping("/formAddStop/{idTrip}")
	public String formNuovaTappa(@PathVariable("idTrip") Long idTrip, Model model) {
		model.addAttribute("stops", this.stopService.findAll());
		model.addAttribute("stop", new Stop());
		model.addAttribute("trip", this.tripRepository.findById(idTrip).get());
		model.addAttribute("idTrip", idTrip);
		return "formAddStop.html";
	}
	
//	@GetMapping("/formAddStop/{idTrip}")
//	public String formNuovoIngrediente(@PathVariable("idTrip") Long idTrip, Model model) {
//	    Trip trip = this.tripRepository.findById(idTrip).orElse(null);
//	    if (trip == null) {
//	        model.addAttribute("messaggioErrore", "Viaggio non trovato");
//	        return "errore.html";
//	    }
//
//	    // Trova tutti gli ingredienti disponibili
//	    List<Stop> tappe = new ArrayList<>();
//	    stopRepository.findAll().forEach(tappe::add);
//
//	    // Rimuovi gli ingredienti già presenti nella ricetta dalla lista degli ingredienti disponibili
//	    tappe.removeAll(trip.getStops());
//
//	    model.addAttribute("trip", trip);
//	    model.addAttribute("stops", tappe);
//	    model.addAttribute("stop", new Stop());
//	    model.addAttribute("idTrip", idTrip);
//	    return "formAddStop.html";
//	}

	@PostMapping("/trip/{idTrip}")
	public String newTappa(@ModelAttribute("stop") Stop stop, @PathVariable("idTrip") Long idTrip, Model model) {
	    Trip trip = tripRepository.findById(idTrip).orElse(null);
	    
	    if (trip.getStops().contains(stop) || trip == null) {
	        model.addAttribute("messaggioErrore", "Questa tappa è già presente nella ricetta.");
	        return "redirect:/trip/"+idTrip;
	    }

	    this.stopRepository.save(stop);
	    trip.getStops().add(stop);
	    tripRepository.save(trip);
	    return "redirect:/trip/"+idTrip;
	}
	
//	@GetMapping("/setStop/{idStop}/{idTrip}")
//	public String formStop(@PathVariable("idStop") Long idStop, @PathVariable("idTrip") Long idTrip, Model model) {
//		Stop stop = this.stopRepository.findById(idStop).get();
//		Trip trip = this.tripRepository.findById(idTrip).get();
//		if(trip == null) {
//			 model.addAttribute("messaggioErrore", "Viaggio non trovato");
//	        return "error"; // Nome del template di errore
//		}
//		stop.setTrip(trip);
//		trip.getStops().add(stop);
//		this.tripRepository.save(trip);
//		this.stopRepository.save(stop);
//		model.addAttribute("stop", stop);
//		model.addAttribute("trip", trip);
//		return "updateTrip.html";
//	}

}