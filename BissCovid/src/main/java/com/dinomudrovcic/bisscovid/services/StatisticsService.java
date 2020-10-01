package com.dinomudrovcic.bisscovid.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinomudrovcic.bisscovid.models.Statistics;
import com.dinomudrovcic.bisscovid.repositories.CasesRepository;
import com.dinomudrovcic.bisscovid.repositories.DeathsRepository;
import com.dinomudrovcic.bisscovid.repositories.StatisticsRepository;
import com.dinomudrovcic.bisscovid.repositories.TestsRepository;

@Service
public class StatisticsService {

	@Autowired
	private StatisticsRepository statisticsRepository;
	
	@Autowired
	private CasesRepository casesRepository;
	
	@Autowired
	private DeathsRepository deathsRepository;
	
	@Autowired
	private TestsRepository testsRepository;
	
	public void saveStatistics(Statistics statistics) {
		casesRepository.save(statistics.getCases());
		deathsRepository.save(statistics.getDeaths());
		testsRepository.save(statistics.getTests());
		statisticsRepository.save(statistics);
	}
	
	public void updateStatistics(Statistics statistics) {
		casesRepository.saveAndFlush(statistics.getCases());
		deathsRepository.saveAndFlush(statistics.getDeaths());
		testsRepository.saveAndFlush(statistics.getTests());
		statisticsRepository.saveAndFlush(statistics);
	}
	
	public void deleteStatisticsById(Long id) {
		Statistics statistics = statisticsRepository.findById(id).orElse(null);
		if (statistics != null) {
			statisticsRepository.deleteById(id);
			casesRepository.deleteById(statistics.getCases().getId());
			deathsRepository.deleteById(statistics.getDeaths().getId());
			testsRepository.deleteById(statistics.getTests().getId());
		}
	}
	
	public List<Statistics> getStatisticsByCountryAndDate(String countryName, String dateFromStr, String dateToStr) {
		if (!(checkDate(dateFromStr) && checkDate(dateToStr))) {
			return null;
		}
		Date dateFrom = Date.valueOf(dateFromStr); 
		Date dateTo = Date.valueOf(dateToStr);
		List<Statistics> filteredStatistics = statisticsRepository.findStatisticsByCountryAndDate(countryName, dateFrom, dateTo);
		return filteredStatistics;
	}
	
	private boolean checkDate(String date) {
		try {
			Date.valueOf(date);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
}
