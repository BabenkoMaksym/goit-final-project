package ua.goit.finalProj2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalApplication {

	public static void main(String[] args) {

		Logger logger = LogManager.getLogger(FinalApplication.class);

		SpringApplication.run(FinalApplication.class, args);

		logger.debug("application started");
	}

}
