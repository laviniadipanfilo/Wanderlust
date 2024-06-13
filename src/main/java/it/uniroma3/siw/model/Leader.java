package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Leader {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String surname;
	private LocalDate birthday;
	private String urlImage;
//	lista dei viaggi che questo capo gruppo offre
	@OneToMany(mappedBy = "leader")
	private List<Trip> trips;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Trip> getTrips() {
		return trips;
	}

	public void setTrips(List<Trip> trips) {
		this.trips = trips;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	public LocalDate getBirthday() {
		return this.birthday;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public boolean equals(Object o) {
		Leader l = (Leader)o;
		return this.getName().equals(l.getName()) && this.getSurname().equals(l.getSurname());
	}
	
	public int hashCode() {
		return this.getName().hashCode() + this.getSurname().hashCode();
	}

}