package com.hnu.pioneer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Activity implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    private String contestName;

    @Column
    private String prize;

    @Column
    private String participant;

    @Column
    private String study;

    @Column
    private String date;

    @Builder
    public Activity(String contestName, String prize, String participant, String study, String date) {
        this.contestName = contestName;
        this.prize = prize;
        this.participant = participant;
        this.study = study;
        this.date = date;
    }
}
