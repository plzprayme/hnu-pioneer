package com.hnu.pioneer.Dto;

import com.hnu.pioneer.domain.Study;
import com.hnu.pioneer.domain.StudyStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


// toEntity
@ToString
@Getter
@NoArgsConstructor
public class StudySaveRequestDto {
    private String studyName;
    private String leader;
    private String goal;
    private String duration;
    private String time;
    private Integer currentStudyMate;
    private StudyStatus status;

    @Builder
    public StudySaveRequestDto(String studyName, String leader, String goal,
                               String duration, String time, Integer currentStudyMate,
                               StudyStatus status) {
        this.studyName = studyName;
        this.time = time;
        this.leader = leader;
        this.goal = goal;
        this.duration = duration;
        this.currentStudyMate = currentStudyMate;
        this.status = status;
    }

    public Study toEntity() {
        return Study.builder()
                .studyName(studyName)
                .leader(leader)
                .goal(goal)
                .duration(duration)
                .currentStudyMate(currentStudyMate)
                .status(status)
                .time(time)
                .build();
    }
}
