package com.dinomudrovcic.bisscovid.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinomudrovcic.bisscovid.models.Tests;
import com.dinomudrovcic.bisscovid.repositories.TestsRepository;

@Service
public class TestsService {

	@Autowired
	TestsRepository repository;
	
	public void saveTests(Tests tests) {
		repository.save(tests);
	}
	
	public void updateTests(Tests tests) {
		repository.saveAndFlush(tests);
	}
	
	public void deleteTestsById(Long id) {
		repository.deleteById(id);
	}
	
}
