package com.hnu.pioneer.domain.jointable;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMemberRepository extends JpaRepository<StudyMemberMapping, Long> {
    List<StudyMemberMapping> findAllByRegisteredStudy(Study study);
    List<StudyMemberMapping> findAllByParticipant(Member member);
}
