package com.hnu.pioneer.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(nullable = false)
    private Long studentNumber;

    @Column(length = 50, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private Role role;

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
}
