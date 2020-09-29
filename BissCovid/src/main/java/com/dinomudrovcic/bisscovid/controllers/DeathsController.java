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

import com.dinomudrovcic.bisscovid.models.Deaths;
import com.dinomudrovcic.bisscovid.services.DeathService;

@RestController
@RequestMapping("/api/deaths")
public class DeathsController {

	@Autowired
	private DeathService service;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLES_USER') or hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> add(@RequestBody Deaths deaths){
		try {
			service.saveDeaths(deaths);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLES_USER') or hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> update(@RequestBody Deaths deaths){
		try {
			service.updateDeaths(deaths);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ROLES_ADMIN')")
	public ResponseEntity<HttpStatus> delete(@RequestBody Long deathsId){
		try {
			service.deleteDeathsById(deathsId);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
