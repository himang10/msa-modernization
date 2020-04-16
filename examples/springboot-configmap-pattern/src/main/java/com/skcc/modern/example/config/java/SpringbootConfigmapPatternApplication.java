package com.skcc.modern.example.config.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringbootConfigmapPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootConfigmapPatternApplication.class, args);
	}

}
