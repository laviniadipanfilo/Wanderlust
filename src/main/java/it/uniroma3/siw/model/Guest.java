package it.uniroma3.siw.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String surname;
	private Integer age;
	@ManyToOne
	private Trip trip;
	
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
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}

	public Trip getTrips() {
		return trip;
	}

	public void setTrips(Trip trips) {
		this.trip = trips;
	}
	
	public boolean equals(Object o) {
		Leader l = (Leader)o;
		return this.getName().equals(l.getName()) && this.getSurname().equals(l.getSurname());
	}
	
	public int hashCode() {
		return this.getName().hashCode() + this.getSurname().hashCode();
	}
}
