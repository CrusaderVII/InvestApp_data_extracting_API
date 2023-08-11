package com.invest.app.data_extract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class )
public class DataExtractApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataExtractApplication.class, args);
	}

}
