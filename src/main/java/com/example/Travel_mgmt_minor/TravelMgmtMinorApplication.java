package com.example.Travel_mgmt_minor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class TravelMgmtMinorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelMgmtMinorApplication.class, args);
	}

}
