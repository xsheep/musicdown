package com.xiaor.archetype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ArchetypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchetypeApplication.class, args);
	}

}
