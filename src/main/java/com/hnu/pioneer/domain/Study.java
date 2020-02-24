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

    @Column(columnDefinition = "integer default 0")
    private Integer currentStudyMate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StudyStatus status = StudyStatus.INCRUIT;

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