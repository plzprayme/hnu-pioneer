package com.hnu.pioneer.service;


import com.hnu.pioneer.dto.StudyMemberMappingDto;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyMemberMapping;
import com.hnu.pioneer.domain.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class StudyMemberService {

    final private StudyMemberRepository repository;

    @Transactional
    public StudyMemberMapping getAfterMapping(Study study, Member member) {
        return repository.save(StudyMemberMappingDto.builder().participant(member).registeredStudy(study).build().toEntity());
    }

    @Transactional(readOnly = true)
    public boolean isAlreadyRegister(Study study, Long memberIdx) {
        List<StudyMemberMapping> mappingList = repository.findAllByRegisteredStudy(study);
        return mappingList.stream().map(StudyMemberMapping::getIdx).anyMatch(id -> id.equals(memberIdx));
    }

    @Transactional(readOnly = true)
    public List<StudyMemberMapping> test(Member member) {
        List<StudyMemberMapping> all = repository.findAll();
        all.stream().map()
        return repository.findAllByParticipant(member);
    }
}
