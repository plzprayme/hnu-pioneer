
package com.hnu.pioneer.service;

import com.hnu.pioneer.Dto.StudyListResponseDto;
import com.hnu.pioneer.Dto.StudySaveRequestDto;
import com.hnu.pioneer.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudyService {
    private final StudyRepository studyRepository;

    @Transactional
    public Long save(StudySaveRequestDto requestDto) {
        return studyRepository.save(requestDto.toEntity()).getIdx();
    }

    @Transactional(readOnly = true)
    public Study getByIdx(Long idx) {
        return studyRepository.findById(idx).get();
    }

    @Transactional(readOnly = true)
    public List<StudyListResponseDto> getIncruitStudy() {
        return studyRepository.findAllByStatus(StudyStatus.INCRUIT).stream()
                .map(StudyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StudyListResponseDto> getStudyByLeader(String leader) {
        return studyRepository.findAllByLeader(leader).stream()
                .map(StudyListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void assignParticipant(Study study, StudyMemberMapping studyMember) {
        study.addParticipants(studyMember);
        study.increaseOneStudyMate();
    }
}