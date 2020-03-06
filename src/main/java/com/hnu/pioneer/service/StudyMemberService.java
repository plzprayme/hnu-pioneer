package com.hnu.pioneer.service;


import com.hnu.pioneer.Dto.StudyMemberMappingDto;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyMemberMapping;
import com.hnu.pioneer.domain.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class StudyMemberService {

    final private StudyMemberRepository repository;

    @Transactional
    public StudyMemberMapping getAfterMapping(Study study, Member member) {
        return repository.save(StudyMemberMappingDto.builder().participant(member).registeredStudy(study).build().toEntity());
    }
}
