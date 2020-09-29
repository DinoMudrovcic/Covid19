package com.dinomudrovcic.bisscovid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinomudrovcic.bisscovid.models.Deaths;
import com.dinomudrovcic.bisscovid.repositories.DeathsRepository;

@Service
public class DeathService {

	@Autowired
	private DeathsRepository repository;
		
	public void saveDeaths(Deaths deaths) {
		repository.save(deaths);
	}
	
	public void updateDeaths(Deaths deaths) {
		repository.saveAndFlush(deaths);
	}
	
	public void deleteDeathsById(Long id) {
		repository.deleteById(id);
	}
	
}
