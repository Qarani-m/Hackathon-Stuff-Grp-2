package com.example.demo;

import com.example.demo.FetchData.FetchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@SpringBootApplication
@EnableScheduling
public class UserApplication {

	private static final Logger logger = LoggerFactory.getLogger(UserApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);

	}
	@Scheduled(fixedDelay = 2000)
	public void fetchDataFromDatabase() {
//		logger.info("Fetching data from database...");
		// Your data fetching logic here
//		logger.info("Data fetched successfully.");
	}




}
