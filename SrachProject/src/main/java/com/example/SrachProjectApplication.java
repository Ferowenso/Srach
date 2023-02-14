package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.backend", "com.example.frontend"})
public class SrachProjectApplication{

	public static void main(String[] args) {
		SpringApplication.run(SrachProjectApplication.class, args);
	}

}
