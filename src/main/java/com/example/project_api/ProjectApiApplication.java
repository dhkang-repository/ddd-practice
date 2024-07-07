package com.example.project_api;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectApiApplication {
	private static Logger logger = LoggerFactory.getLogger(ProjectApiApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ProjectApiApplication.class, args);
	}

	@PostConstruct
	public void post() {
		logger.info("test test");
		logger.warn("test test");
		logger.error("test test");
		logger.trace("test test");
		logger.debug("test test");
	}
}
