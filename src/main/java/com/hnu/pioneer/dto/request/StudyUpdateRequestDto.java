package com.hnu.pioneer.dto.request;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.StudyStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StudyUpdateRequestDto {
    private String studyName;
    private String goal;
    private String duration;
    private String time;

    @Builder
    public StudyUpdateRequestDto(String studyName, String goal, String duration, String time) {
        this.studyName = studyName;
        this.goal = goal;
        this.duration = duration;
        this.time = time;
    }
}
