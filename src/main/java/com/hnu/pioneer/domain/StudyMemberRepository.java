package com.hnu.pioneer.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMemberMapping, Long> {
    List<StudyMemberMapping> findAllByRegisteredStudy(Study study);
}
