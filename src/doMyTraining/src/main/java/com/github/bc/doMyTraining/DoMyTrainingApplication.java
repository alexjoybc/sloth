package com.github.bc.doMyTraining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoMyTrainingApplication {

	private static Logger logger = LoggerFactory
			.getLogger(DoMyTrainingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DoMyTrainingApplication.class, args);
	}


}
