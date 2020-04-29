package com.hnu.pioneer.domain;

import com.hnu.pioneer.domain.jointable.StudyMemberMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table
public class Member extends BaseTimeEntity{
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

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Study> createStudies = new ArrayList<>();

    @OneToMany(mappedBy = "participant", orphanRemoval = true)
    private Set<StudyMemberMapping> registeredStudies = new HashSet<>();

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

    public void addRegisteredStudy(StudyMemberMapping mapper) {
        this.registeredStudies.add(mapper);
    }

    public void removeRegisteredStudy(StudyMemberMapping registeredStudy) {
        this.registeredStudies.remove(registeredStudy);
        registeredStudy.setParticipant(null);
    }

    public void changePassword(String encodedPassword) {
        password = encodedPassword;
    }

    public Long updateRole(Role role) {
        this.role = role;
        return idx;
    }
}
