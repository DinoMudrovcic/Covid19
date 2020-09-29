package com.dinomudrovcic.bisscovid.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinomudrovcic.bisscovid.models.Tests;
import com.dinomudrovcic.bisscovid.services.TestsService;

@RestController
@RequestMapping("/api/tests")
public class TestsController {
	
	@Autowired
	private TestsService service;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLES_USER') or hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> add(@RequestBody Tests tests){
		try {
			service.saveTests(tests);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLES_USER') or hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> update(@RequestBody Tests tests){
		try {
			service.updateTests(tests);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> delete(@RequestBody Long testsId){
		try {
			service.deleteTestsById(testsId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
