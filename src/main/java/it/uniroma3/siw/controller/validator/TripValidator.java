package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Trip;
import it.uniroma3.siw.repository.TripRepository;

@Component
public class TripValidator implements Validator {
	
	@Autowired private TripRepository tripRepository;

	@Override
	public void validate(Object o, Errors errors) {
		Trip trip = (Trip)o;
		if (trip.getName()!=null && trip.getId()!=null 
				&& tripRepository.existsByNameAndId(trip.getName(), trip.getId())) {
			errors.reject("trip.duplicate");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Trip.class.equals(aClass);
	}
}