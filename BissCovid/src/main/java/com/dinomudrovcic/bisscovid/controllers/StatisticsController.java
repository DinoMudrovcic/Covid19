package com.dinomudrovcic.bisscovid.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinomudrovcic.bisscovid.models.Statistics;
import com.dinomudrovcic.bisscovid.services.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	
	@Autowired
	private StatisticsService service;
	
	@GetMapping("/{countryName}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<Statistics> getStatistics(@PathVariable String countryName) {
		return new ResponseEntity<>(service.getStatisticsByCountryName(countryName), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> add(@RequestBody Statistics statistics){
		try {
			service.saveStatistics(statistics);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/addList")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> addList(@RequestBody List<Statistics> statisticsList){
		try {
			service.saveStatisticsList(statisticsList);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> update(@RequestBody Statistics statistics){
		try {
			service.updateStatistics(statistics);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/updateList")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> updateList(@RequestBody List<Statistics> statisticsList){
		try {
			service.updateStatisticsList(statisticsList);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> delete(@RequestBody Statistics statistics){
		try {
			service.deleteStatisticsById(statistics.getId());
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteList")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<HttpStatus> deleteList(@RequestBody List<Statistics> statisticsList){
		try {
			service.deleteStatisticsList(statisticsList);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/searchByCountryAndDate/{countryName}/{dateFrom}/{dateTo}")
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Statistics>> getStatisticsByCountryAndDate(@PathVariable String countryName, 
			@PathVariable String dateFrom, @PathVariable String dateTo) {
		List<Statistics> responseStatistics = service.getStatisticsByCountryAndDate(countryName, dateFrom, dateTo);
		return responseStatistics == null ? 
				new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) :
				new ResponseEntity<>(responseStatistics, HttpStatus.OK);
	}

}
