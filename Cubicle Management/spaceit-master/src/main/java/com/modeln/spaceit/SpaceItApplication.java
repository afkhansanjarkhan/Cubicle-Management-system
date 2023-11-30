package com.modeln.spaceit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.modeln.spaceit.controllers","com.modeln.spaceit.services",
        "com.modeln.spaceit.configurations", "com.modeln.spaceit.utils"})
@EntityScan(basePackages = {"com.modeln.spaceit.entities"})
public class SpaceItApplication {

	public static void main(String[] args) {
		System.out.println("Application is getting started");
		SpringApplication.run(SpaceItApplication.class, args);
	}
}
