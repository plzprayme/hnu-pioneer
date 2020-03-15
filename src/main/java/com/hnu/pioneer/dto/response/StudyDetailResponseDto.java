package com.hnu.pioneer.dto.response;

import com.hnu.pioneer.domain.Study;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StudyDetailResponseDto {
    private Long idx;
    private String studyName;
    private String leader;
    private String duration;
    private String time;
    private String goal;
    private Integer currentStudyMate;
    private String participantNames;

    public StudyDetailResponseDto(Study study) {
        this.idx = study.getIdx();
        this.studyName = study.getStudyName();
        this.leader = study.getLeader();
        this.duration = study.getDuration();
        this.time = study.getTime();
        this.goal = study.getGoal();
        this.currentStudyMate = study.getCurrentStudyMate();
    }
    
    public void setParticipantNames(String name) {
        this.participantNames = name;
    }
}
