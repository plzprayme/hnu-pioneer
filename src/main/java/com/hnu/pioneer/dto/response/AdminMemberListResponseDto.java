package com.hnu.pioneer.dto.response;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AdminMemberListResponseDto {
    private Long idx;
    private String email;
    private String name;
    private Long studentNumber;
    private String registerStudy;
    private String role;

    public AdminMemberListResponseDto(Member member) {
        this.idx = member.getIdx();
        this.email = member.getEmail();
        this.studentNumber = member.getStudentNumber();
        this.name = member.getName();
        this.registerStudy = getRegisterStudiesName(member.getRegisteredStudies());
        this.role = member.getRole().getTitle();
    }

    private String getRegisterStudiesName(Set<StudyMemberMapping> registerStudy) {
        return registerStudy.stream()
                .map(StudyMemberMapping::getRegisteredStudy)
                .map(Study::getStudyName)
                .collect(Collectors.joining(" 스터디, "));
    }
}
