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

    @Column(length = 50, nullable = false)
    private String password;

    @Column(nullable = false)
    private Long studentNumber;

    @Column(length = 50, nullable = false)
    private String email;

    @Builder
    public Member(String name, String password, Long studentNumber, String email) {
        this.name = name;
        this.password = password;
        this.studentNumber = studentNumber;
        this.email = email;
    }
}
