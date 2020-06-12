package com.bridgelabz.fundoonotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
public class NotesMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesMicroserviceApplication.class, args);
	}

}
