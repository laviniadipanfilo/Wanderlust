package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String location;
	private Float price;
	private String transport;
	private LocalDate arrivalDay;
	private LocalDate lastDay;
	private String urlImage;
	@Column(length=2000)
	private String description;
	@ManyToOne
	private Leader leader;
	@ManyToMany
	private List<Stop> stops;
	@OneToMany
	private List<Guest> guests;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Leader getLeader() {
		return leader;
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public LocalDate getArrivalDay() {
		return arrivalDay;
	}

	public void setArrivalDay(LocalDate arrivalDay) {
		this.arrivalDay = arrivalDay;
	}

	public LocalDate getLastDay() {
		return lastDay;
	}

	public void setLastDay(LocalDate lastDay) {
		this.lastDay = lastDay;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	
	public boolean equals(Object o) {
		Trip t = (Trip)o;
		return this.getName().equals(t.getName());
	}
	
	public int hashCode() {
		return this.getName().hashCode();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
