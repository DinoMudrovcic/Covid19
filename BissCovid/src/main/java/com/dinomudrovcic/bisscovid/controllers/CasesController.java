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

import com.dinomudrovcic.bisscovid.models.Cases;
import com.dinomudrovcic.bisscovid.services.CasesService;

@RestController
@RequestMapping("/api/cases")
public class CasesController {

	@Autowired
	private CasesService service;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLES_USER') or hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> add(@RequestBody Cases cases){
		try {
			service.saveCases(cases);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLES_USER') or hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> update(@RequestBody Cases cases){
		try {
			service.updateCases(cases);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> delete(@RequestBody Long casesId){
		try {
			service.deleteCases(casesId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
