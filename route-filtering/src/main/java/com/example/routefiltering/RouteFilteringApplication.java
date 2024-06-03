package com.example.routefiltering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ServletComponentScan	
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "com.example.routefiltering")
public class RouteFilteringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteFilteringApplication.class, args);
	}

}
