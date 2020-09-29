package com.dinomudrovcic.bisscovid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

import com.dinomudrovcic.bisscovid.services.DataService;

@SpringBootApplication
@EntityScan
@ComponentScan
@EnableJpaRepositories
public class BissCovidApplication {
	
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}
	
	@Autowired
	private DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(BissCovidApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadData() {
		dataService.loadData();
	}

}
