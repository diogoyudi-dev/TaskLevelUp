package com.taskLevel.up;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UpApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpApplication.class, args);
	}

}
