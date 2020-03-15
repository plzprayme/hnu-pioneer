package com.hnu.pioneer.dto.response;

import com.hnu.pioneer.domain.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class CreateStudyListResponseDto {
    private Long idx;
    private String studyName;
    private String leader;
    private Integer currentStudyMate;
    private String createdDate;

    public CreateStudyListResponseDto(Study study) {
        this.idx = study.getIdx();
        this.studyName = study.getStudyName();
        this.leader = study.getLeader();
        this.currentStudyMate = study.getCurrentStudyMate();
        this.createdDate = study.getCreatedDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
