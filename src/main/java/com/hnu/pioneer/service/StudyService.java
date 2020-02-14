package com.hnu.pioneer.service;

import com.hnu.pioneer.Dto.StudySaveRequestDto;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyService {
    private final StudyRepository studyRepository;

    @Transactional
    public Long save(StudySaveRequestDto requestDto) {
        return studyRepository.save(requestDto.toEntity()).getIdx();
    }

//    @Transactional
//    public List<Study> getIncruitStudy() {
//
//    }
}
