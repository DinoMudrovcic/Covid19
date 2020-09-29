package com.dinomudrovcic.bisscovid.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.dinomudrovcic.bisscovid.models.CountriesResponse;
import com.dinomudrovcic.bisscovid.models.Country;
import com.dinomudrovcic.bisscovid.models.HistoryResponse;
import com.dinomudrovcic.bisscovid.models.Statistics;
import com.dinomudrovcic.bisscovid.repositories.CasesRepository;
import com.dinomudrovcic.bisscovid.repositories.CountryRepository;
import com.dinomudrovcic.bisscovid.repositories.DeathsRepository;
import com.dinomudrovcic.bisscovid.repositories.StatisticsRepository;
import com.dinomudrovcic.bisscovid.repositories.TestsRepository;
import com.dinomudrovcic.bisscovid.utils.EndpointConstants;

@Service
public class DataService {
	
	private static final Logger logger = LoggerFactory.getLogger(DataService.class);
	
	private CasesRepository casesRepository;
	private CountryRepository countryRepository;
	private DeathsRepository deathsRepository;
	private StatisticsRepository statisticsRepository;
	private TestsRepository testsRepository;
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	public DataService(CasesRepository casesRepository, CountryRepository countryRepository, DeathsRepository deathsRepository,
			StatisticsRepository statisticsRepository, TestsRepository testsRepository, WebClient.Builder builder) {
		this.casesRepository = casesRepository;
		this.countryRepository = countryRepository;
		this.deathsRepository = deathsRepository;
		this.statisticsRepository = statisticsRepository;
		this.testsRepository = testsRepository;
		this.webClientBuilder = builder;
	}
	
	@Transactional
	public void loadData() {
		
		logger.info("Loading data started.");
		
		//save all countries in DB
		CountriesResponse countriesResponse = webClientBuilder.build()
			.get()
			.uri(EndpointConstants.COUNTRIES_ENDPOINT)
			.header(EndpointConstants.RAPIDAPI_HOST_NAME, EndpointConstants.RAPIDAPI_HOST_VALUE)
			.header(EndpointConstants.RAPIDAPI_KEY_NAME, EndpointConstants.RAPIDAPI_KEY_VALUE)
			.retrieve()
			.bodyToMono(CountriesResponse.class)
			.block();
		List<String> countriesStr = countriesResponse.getResponse();
		List<Country> countries = new ArrayList<>();
		countriesStr.stream().forEach(country -> {
			Country countryObj = new Country();
			countryObj.setName(country);
			Country savedCountry = countryRepository.save(countryObj);
			if (savedCountry != null) {
				countries.add(savedCountry);
			}
		});
		
		for (int i = 0; i < 3; i++) {
			Country country = countries.get(i);
			String countryName = country.getName();
			HistoryResponse historyResponse = webClientBuilder.build()
					.get()
					.uri(EndpointConstants.HISTORY_ENDPOINT + "?country=" + countryName)
					.header(EndpointConstants.RAPIDAPI_HOST_NAME, EndpointConstants.RAPIDAPI_HOST_VALUE)
					.header(EndpointConstants.RAPIDAPI_KEY_NAME, EndpointConstants.RAPIDAPI_KEY_VALUE)
					.retrieve()
					.bodyToMono(HistoryResponse.class)
					.block();
			List<Statistics> historyList = historyResponse.getResponse();
			if (!historyList.isEmpty()) {
				historyList.stream().forEach(history -> {
					saveStatisticToDatabase(history, country);
				});
			}
		}
		
		//iterate through countries and get response for current statistics and history data
		countries.stream().forEach(country -> {
			String countryName = country.getName();
//			StatisticsResponse statisticsResponse = webClientBuilder.build()
//					.get()
//					.uri(EndpointConstants.STATISTICS_ENDPOINT + "?country=" + countryName)
//					.header(EndpointConstants.RAPIDAPI_HOST_NAME, EndpointConstants.RAPIDAPI_HOST_VALUE)
//					.header(EndpointConstants.RAPIDAPI_KEY_NAME, EndpointConstants.RAPIDAPI_KEY_VALUE)
//					.retrieve()
//					.bodyToFlux(StatisticsResponse.class)
//					.blockLast();
//			if (!statisticsResponse.getResponse().isEmpty()) {
//				saveStatisticToDatabase(statisticsResponse.getResponse().get(0), country); //we get only one reponse with current statistics, when search by country
//			}
			
			HistoryResponse historyResponse = webClientBuilder.build()
					.get()
					.uri(EndpointConstants.HISTORY_ENDPOINT + "?country=" + countryName)
					.header(EndpointConstants.RAPIDAPI_HOST_NAME, EndpointConstants.RAPIDAPI_HOST_VALUE)
					.header(EndpointConstants.RAPIDAPI_KEY_NAME, EndpointConstants.RAPIDAPI_KEY_VALUE)
					.retrieve()
					.bodyToMono(HistoryResponse.class)
					.block();
			List<Statistics> historyList = historyResponse.getResponse();
			if (!historyList.isEmpty()) {
				historyList.stream().forEach(history -> {
					saveStatisticToDatabase(history, country);
				});
			}
		});

		logger.info("Loading data finished.");
	}

	@Transactional
	private void saveStatisticToDatabase(Statistics statistics, Country country) {
		casesRepository.save(statistics.getCases());
		deathsRepository.save(statistics.getDeaths());
		testsRepository.save(statistics.getTests());
		statisticsRepository.save(statistics);
	}
}
