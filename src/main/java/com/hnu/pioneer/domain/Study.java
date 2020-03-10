package com.hnu.pioneer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties({"member"})
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

    @ManyToOne
    @JoinColumn(name = "member_idx", foreignKey = @ForeignKey(name = "FK_MEMBER_IDX"))
    private Member member;

    @OneToMany(mappedBy = "participant")
    private List<StudyMemberMapping> participants = new ArrayList<>();

    @Builder
    public Study(String studyName, String leader, String time,
                 String goal, String duration, Integer currentStudyMate, StudyStatus status, Member member) {
        this.member = member;
        this.studyName = studyName;
        this.time = time;
        this.leader = leader;
        this.goal = goal;
        this.duration = duration;
        this.currentStudyMate = currentStudyMate;
        this.status = status;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void addParticipants(StudyMemberMapping mapper) {
        participants.add(mapper);
        mapper.setRegisteredStudy(this);
    }

    public void increaseOneStudyMate() {
        this.currentStudyMate++;
    }
}