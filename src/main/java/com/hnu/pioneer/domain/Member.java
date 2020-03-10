package com.hnu.pioneer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Member {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private Long studentNumber;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Study> createStudies = new ArrayList<>();

    @OneToMany(mappedBy = "registeredStudy")
    private List<StudyMemberMapping> registeredStuies = new ArrayList<>();

    @Builder
    public Member(String name, String password, Long studentNumber, String email, Role role) {
        this.name = name;
        this.password = password;
        this.studentNumber = studentNumber;
        this.email = email;
        this.role = role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }

    public boolean isStudent() {
        return role == Role.STUDENT;
    }

    public boolean isLeader() {
        return role == Role.LEADER;
    }

    public void addCreateStudies(Study study) {
        createStudies.add(study);
        study.setMember(this);
    }

    public void addRegisteredStudy(StudyMemberMapping mapper) {
        registeredStuies.add(mapper);
        mapper.setParticipant(this);
    }
}
