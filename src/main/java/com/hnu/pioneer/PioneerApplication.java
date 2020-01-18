package com.hnu.pioneer;

import com.hnu.pioneer.domain.Activity;
import com.hnu.pioneer.repository.ActivityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PioneerApplication {

	public static void main(String[] args) {
	    SpringApplication.run(PioneerApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(ActivityRepository activityRepository) throws Exception {
		return	(args) -> {
			 for (int i = 0; i < 200; i++) {
				 activityRepository.save(Activity.builder()
						 .contestName("I'm Name")
						 .prize("email@email.com")
						 .participant("황성찬,이헌주,이성현")
						 .study("알고리즘")
						 .date("2019학년도 1학기")
						 .build());
			 }
		};
	}


}
