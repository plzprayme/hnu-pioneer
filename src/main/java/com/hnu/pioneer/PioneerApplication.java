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

	@Bean
	public CommandLineRunner runner(StudyRepository studyRepository
//			ActivityRepository activityRepository
	) throws Exception {
//		return	(args) -> {
//			 for (int i = 0; i < 200; i++) {
//				 activityRepository.save(Activity.builder()
//						 .contestName("I'm Name")
//						 .prize("email@email.com")
//						 .participant("황성찬,이헌주,이성현")
//						 .study("알고리즘")
//						 .date("2019학년도 1학기")
//						 .build());
//			 }
//		};

		return	(args) -> {
			for (int i = 0; i < 2; i++) {
				studyRepository.save(Study.builder()
					.studyName("파이썬")
					.leader("이헌주")
					.date("2019.03.10~2019.04.10")
					.goal("파이썬의 기본 문법을 익히고 최종먹으로 파싱 프로그램을 구현한다.")
					.place("학교 근청")
					.maxStudyMate(20)
					.currentStudyMate(0)
					.status(StudyStatus.INCRUIT)
					.build());
			}
		};
	}


}
