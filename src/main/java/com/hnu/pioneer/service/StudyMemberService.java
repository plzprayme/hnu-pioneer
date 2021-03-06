package com.hnu.pioneer.service;

import com.hnu.pioneer.dto.StudyMemberMappingDto;
import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import com.hnu.pioneer.domain.jointable.StudyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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
        return mappingList.stream()
                .map(StudyMemberMapping::getParticipant)
                .anyMatch(participant -> participant.getIdx().equals(memberIdx));
    }
}
