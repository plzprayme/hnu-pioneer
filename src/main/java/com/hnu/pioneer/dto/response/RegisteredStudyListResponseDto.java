package com.hnu.pioneer.dto.response;

import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class RegisteredStudyListResponseDto {
    private Long idx;
    private String studyName;
    private String leader;
    private Integer currentStudyMate;
    private String createdDate;

    public RegisteredStudyListResponseDto(StudyMemberMapping studyMember) {
        this.idx = studyMember.getRegisteredStudy().getIdx();
        this.studyName = studyMember.getRegisteredStudy().getStudyName();
        this.leader = studyMember.getRegisteredStudy().getLeader();
        this.currentStudyMate = studyMember.getRegisteredStudy().getCurrentStudyMate();
        this.createdDate = studyMember.getRegisteredStudy().getCreatedDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

