package com.dinomudrovcic.bisscovid.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.dinomudrovcic.bisscovid.models.HistoryResponse;
import com.dinomudrovcic.bisscovid.models.Statistics;
import com.dinomudrovcic.bisscovid.repositories.CasesRepository;
import com.dinomudrovcic.bisscovid.repositories.DeathsRepository;
import com.dinomudrovcic.bisscovid.repositories.StatisticsRepository;
import com.dinomudrovcic.bisscovid.repositories.TestsRepository;
import com.dinomudrovcic.bisscovid.utils.EndpointConstants;

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
	
	@Autowired
	private WebClient.Builder builder;

	public Statistics getStatisticsByCountryName(String countryName) {
		return null;
	}
	
	public void saveStatistics(Statistics statistics) {
		casesRepository.save(statistics.getCases());
		deathsRepository.save(statistics.getDeaths());
		testsRepository.save(statistics.getTests());
		statisticsRepository.save(statistics);
	}

	@Transactional
	public void saveStatisticsList(List<Statistics> statisticsList) {
		statisticsList.stream().forEach(statistics -> {
			saveStatistics(statistics);
		});
	}

	
	public void updateStatistics(Statistics statistics) {
		casesRepository.saveAndFlush(statistics.getCases());
		deathsRepository.saveAndFlush(statistics.getDeaths());
		testsRepository.saveAndFlush(statistics.getTests());
		statisticsRepository.saveAndFlush(statistics);
	}
	
	@Transactional
	public void updateStatisticsList(List<Statistics> statisticsList) {
		statisticsList.stream().forEach(statistics -> {
			updateStatistics(statistics);
		});
	}
	
	public void deleteStatisticsById(Long id) {
		Statistics statistics = statisticsRepository.findById(id).orElse(null);
		if (statistics != null) {
			statisticsRepository.delete(statistics);
			casesRepository.delete(statistics.getCases());
			deathsRepository.delete(statistics.getDeaths());
			testsRepository.delete(statistics.getTests());
		}
	}
	
	@Transactional
	public void deleteStatisticsList(List<Statistics> statisticsList) {
		statisticsList.stream().forEach(statistics -> {
			deleteStatisticsById(statistics.getId());
		});
	}
	
	public List<Statistics> getStatisticsByCountryAndDate(String countryName, String dateFromStr, String dateToStr) {
		if (!(checkDate(dateFromStr) && checkDate(dateToStr))) {
			return null;
		}
		
		LocalDate dateFrom = LocalDate.parse(dateFromStr);
		LocalDate dateTo = LocalDate.parse(dateToStr);
		
		List<Statistics> historyList = new ArrayList<>();
		
		for (LocalDate date = dateFrom; date.isBefore(dateTo); date = date.plusDays(1)) {
			HistoryResponse historyResponse = builder.build()
					.get()
					.uri(EndpointConstants.HISTORY_ENDPOINT + "?country=" + countryName + "&day=" + date.toString())
					.header(EndpointConstants.RAPIDAPI_HOST_NAME, EndpointConstants.RAPIDAPI_HOST_VALUE)
					.header(EndpointConstants.RAPIDAPI_KEY_NAME, EndpointConstants.RAPIDAPI_KEY_VALUE)
					.retrieve()
					.bodyToMono(HistoryResponse.class)
					.block();
			historyResponse.getResponse().stream().forEach(item -> {
				historyList.add(item);
			});
		}
		
		return historyList;
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
