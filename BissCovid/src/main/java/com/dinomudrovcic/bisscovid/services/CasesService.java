package com.dinomudrovcic.bisscovid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinomudrovcic.bisscovid.models.Cases;
import com.dinomudrovcic.bisscovid.repositories.CasesRepository;

@Service
public class CasesService {
	
	@Autowired
	private CasesRepository repository;
	
	public void saveCases(Cases cases) {
		repository.save(cases);
	}
	
	public void updateCases(Cases cases) {
		repository.saveAndFlush(cases);
	}
	
	public void deleteCases(Long id) {
		repository.deleteById(id);
	}
}
