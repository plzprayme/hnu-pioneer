package com.hnu.pioneer;

import com.hnu.pioneer.domain.Activity;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyStatus;
import com.hnu.pioneer.repository.ActivityRepository;
import com.hnu.pioneer.repository.StudyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PioneerApplication {

	public static void main(String[] args) {
	    SpringApplication.run(PioneerApplication.class, args);
	}
}
