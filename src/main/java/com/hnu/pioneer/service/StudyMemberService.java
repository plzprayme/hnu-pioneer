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
        return mappingList.stream().map(StudyMemberMapping::getIdx).anyMatch(id -> id.equals(memberIdx));
    }

    @Transactional(readOnly = true)
    public List<StudyMemberMapping> test(Member member) {
        List<StudyMemberMapping> all = repository.findAll();
        all.stream().filter(a -> a.equals(member.getIdx())).collect(Collectors.toList());
        return repository.findAllByParticipant(member);
    }

    @Transactional
    public Long removeRegisterStudy(Long studentNumber, Long studyNumber) {

        StudyMemberMapping a = repository.findAll().get(0);

//        System.out.println(studentNumber);
//        System.out.println(studyNumber);
//        System.out.println(a.getParticipant().getIdx());
//        System.out.println(a.getRegisteredStudy().getIdx());

//        repository.findAll().stream().

        for (StudyMemberMapping studyMember : repository.findAll()) {

            if (studyMember.getParticipant().getIdx().equals(studentNumber)
                    && studyMember.getRegisteredStudy().getIdx().equals(studyNumber)) {
                studyMember.getRegisteredStudy().removeParticipant(studyMember);
                studyMember.getParticipant().removeRegisteredStudy(studyMember);

                return studyMember.getIdx();
            }

        }

        return 0L;
    }
}
