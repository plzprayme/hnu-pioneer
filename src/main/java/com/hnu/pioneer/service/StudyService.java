package com.hnu.pioneer.service;

import com.hnu.pioneer.Dto.StudyListResponseDto;
import com.hnu.pioneer.Dto.StudySaveRequestDto;
import com.hnu.pioneer.domain.StudyStatus;
import com.hnu.pioneer.domain.StudyRepository;
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

    @Transactional
    public List<StudyListResponseDto> getIncruitStudy() {
        return studyRepository.findAllByStatus(StudyStatus.INCRUIT)
                .stream().map(StudyListResponseDto::new)
                .collect(Collectors.toList());
    }
}
