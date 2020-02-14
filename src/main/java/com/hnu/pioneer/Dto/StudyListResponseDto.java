package com.hnu.pioneer.Dto;

import com.hnu.pioneer.domain.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class StudyListResponseDto {
    private String studyName;
    private String leader;
    private String duration;
    private String time;
    private String goal;
    private Integer currentStudyMate;

    public StudyListResponseDto(Study study) {
        this.studyName = study.getStudyName();
        this.leader = study.getLeader();
        this.duration = study.getDuration();
        this.time = study.getTime();
        this.goal = study.getGoal();
        this.currentStudyMate = study.getCurrentStudyMate();
    }
}
