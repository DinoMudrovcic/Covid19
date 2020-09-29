package com.dinomudrovcic.bisscovid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EntityScan
@ComponentScan
@EnableJpaRepositories
public class BissCovidApplication {
	
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}
	
//	@Autowired
//	private DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(BissCovidApplication.class, args);
	}
	
//	@EventListener(ApplicationReadyEvent.class)
//	public void loadData() {
//		dataService.loadData();
//	}

}
