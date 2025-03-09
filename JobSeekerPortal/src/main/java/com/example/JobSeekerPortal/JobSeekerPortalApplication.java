package com.example.JobSeekerPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JobSeekerPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSeekerPortalApplication.class, args);
	}

}
