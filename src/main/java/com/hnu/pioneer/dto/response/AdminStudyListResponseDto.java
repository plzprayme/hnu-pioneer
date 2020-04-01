package com.hnu.pioneer.dto.response;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyStatus;
import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class AdminStudyListResponseDto {
    private Long idx;
    private String studyName;
    private String leader;
    private Integer currentStudyMate;
    private String participantNames;
    private String duration;
    private String status;

    private String getParticipantNames(Set<StudyMemberMapping> participants) {
        return participants.stream()
                .map(StudyMemberMapping::getParticipant)
                .map(Member::getName)
                .collect(Collectors.joining(", "));
    }

    public AdminStudyListResponseDto(Study study) {
        this.idx = study.getIdx();
        this.studyName = study.getStudyName();
        this.leader = study.getLeader();
        this.currentStudyMate = study.getCurrentStudyMate();
        this.participantNames = getParticipantNames(study.getParticipants());
        this.duration = study.getDuration();
        this.status = study.getStatus().getStatus();
    }
}
