package com.linkc.linkcbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class LinkcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkcBackendApplication.class, args);
	}
}
