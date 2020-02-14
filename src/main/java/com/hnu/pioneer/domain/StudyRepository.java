package com.hnu.pioneer.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findAllByStatus(StudyStatus incruit);
}
