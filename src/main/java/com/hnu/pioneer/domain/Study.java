package com.hnu.pioneer.domain;

import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import com.hnu.pioneer.dto.request.StudyUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Study extends BaseTimeEntity {
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
    @JoinColumn(name = "leader_idx")
    private Member member;

    @OneToMany(mappedBy = "registeredStudy", orphanRemoval = true)
    private Set<StudyMemberMapping> participants = new HashSet<>();

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
        this.participants.add(mapper);
    }

    public void increaseOneStudyMate() {
        this.currentStudyMate++;
    }

    public void removeParticipant(StudyMemberMapping participant) {
        this.participants.remove(participant);
        participant.setRegisteredStudy(null);
        this.currentStudyMate--;
    }

    public void update(StudyUpdateRequestDto requestDto) {
        studyName = requestDto.getStudyName();
        duration = requestDto.getDuration();
        goal = requestDto.getGoal();
        time = requestDto.getTime();
    }

    public void close() {
        status = StudyStatus.CLOSED;
    }
}