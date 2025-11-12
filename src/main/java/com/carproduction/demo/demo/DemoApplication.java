package com.carproduction.demo.demo;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.carproduction.demo.demo.controllers","com.carproduction.demo.demo.services",
"com.carproduction.demo.demo.entities","com.carproduction.demo.demo.repositories",
"com.carproduction.demo.demo.configs"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
