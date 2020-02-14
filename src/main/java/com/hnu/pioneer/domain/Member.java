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

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer studentNumber;

    @Column(length = 20, nullable = false)
    private String email;

    @Builder
    public Member(String name, String password, Integer studentNumber, String email) {
        this.name = name;
        this.password = password;
        this.studentNumber = studentNumber;
        this.email = email;
    }
}
