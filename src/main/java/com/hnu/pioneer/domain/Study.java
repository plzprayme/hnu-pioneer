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
    private Integer Idx;

    @Column
    private String studyName;

    @Column
    private String leader;

    @Column
    private String goal;

    @Column
    private String date;

    @Column
    private String place;

    @Column
    private Integer maxStudyMate;

    @Column
    private Integer currentStudyMate;

    @Column
    private StudyStatus status;

    @Builder
    public Study(String studyName, String leader, String goal, String date, String place, Integer maxStudyMate, Integer currentStudyMate, StudyStatus status) {
        this.studyName = studyName;
        this.leader = leader;
        this.goal = goal;
        this.date = date;
        this.place = place;
        this.maxStudyMate = maxStudyMate;
        this.currentStudyMate = currentStudyMate;
        this.status = status;
    }
}