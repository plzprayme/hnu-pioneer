package com.hnu.pioneer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Study implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 60)
    private String studyName;

    @Column(length = 30)
    private String leader;

    @Column(columnDefinition = "TEXT")
    private String goal;

    @Column(length = 30)
    private String duration;

    @Column(length = 30)
    private String time;

    @Column
    private Integer currentStudyMate;

    @Column
    private StudyStatus status;

    @Builder
    public Study(String studyName, String leader, String time,
                 String goal, String duration, Integer currentStudyMate, StudyStatus status) {
        this.studyName = studyName;
        this.time = time;
        this.leader = leader;
        this.goal = goal;
        this.duration = duration;
        this.currentStudyMate = currentStudyMate;
        this.status = status;
    }
}