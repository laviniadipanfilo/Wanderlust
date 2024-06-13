package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Leader;
import it.uniroma3.siw.model.Trip;
import it.uniroma3.siw.repository.LeaderRepository;
import it.uniroma3.siw.repository.TripRepository;
import it.uniroma3.siw.service.LeaderService;

@Controller
public class LeaderController {
	
	@Autowired LeaderService leaderService;
	@Autowired LeaderRepository leaderRepository;
	@Autowired TripRepository tripRepository;

	@GetMapping("/leader/{idLeader}")
	public String showLeader(@PathVariable("idLeader") Long idLeader, Model model) {
		model.addAttribute("leader", this.leaderService.findById(idLeader));
		return "leader.html";
	}
	
	@GetMapping("/leaders")
	public String showLeaders(Model model) {
		model.addAttribute("leaders", this.leaderService.findAll());
		return "leaders.html";
	}
	
	@GetMapping("/formNewLeader")
	public String formNuovoLeader(Model model) {
		model.addAttribute("leader", new Leader());
		return "formNewLeader.html";
	}

	@PostMapping("/leader")
	public String newLeader(@ModelAttribute("leader") Leader leader, Model model) {
		if (!leaderRepository.existsByNameAndSurname(leader.getName(), leader.getSurname())) {
			this.leaderService.save(leader);
			if(leader.getUrlImage() == null) {
				leader.setUrlImage("https://i.pinimg.com/736x/f8/47/02/f8470230ebacb2bfb9924ea13e3b5427.jpg");
			}
			return "redirect:leader/"+leader.getId();
		} else {
			model.addAttribute("messaggioErrore", "Questo leader esiste già");
			return "formNewLeader.html";
		}
	}

	@PostMapping("/deleteLeader/{idLeader}")
	public String cancellaRicetta(@PathVariable("idLeader") Long idLeader, Model model) {
		Leader leader = this.leaderRepository.findById(idLeader).get();
		for(Trip i: leader.getTrips()) {
			i.setLeader(null);
		}
		this.leaderService.delete(idLeader);
    	model.addAttribute("leaders", this.leaderService.findAll());
		return "redirect:/leaders";
	}
	
}