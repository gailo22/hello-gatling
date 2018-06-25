package com.gailo22.gatling.hellogatling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class HelloGatlingApplication {

	@Bean
	ExecutorService executor() {
		return Executors.newFixedThreadPool(10);
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloGatlingApplication.class, args);
	}
}
