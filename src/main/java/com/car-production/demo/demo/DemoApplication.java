package com.security.demo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.security.demo.demo.controllers","com.security.demo.demo.services",
"com.security.demo.demo.entities","com.security.demo.demo.repositories",
"com.security.demo.demo.configs"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
