package com.dinomudrovcic.bisscovid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinomudrovcic.bisscovid.models.Country;
import com.dinomudrovcic.bisscovid.repositories.CountryRepository;

@Service
public class CountryService {

	@Autowired
	private CountryRepository repository;
	
	public void saveCountry(Country country) {
		repository.save(country);
	}
	
	public void updateCountry(Country country) {
		repository.saveAndFlush(country);
	}
	
	public void deleteCountryById(Long id) {
		repository.deleteById(id);
	}
	
	
}
