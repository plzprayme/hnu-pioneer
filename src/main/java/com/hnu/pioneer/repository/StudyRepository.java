package com.hnu.pioneer.repository;

import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Study findByStatus(StudyStatus status);
}
