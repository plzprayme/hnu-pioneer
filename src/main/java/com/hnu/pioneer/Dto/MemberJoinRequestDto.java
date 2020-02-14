package com.hnu.pioneer.Dto;

import com.hnu.pioneer.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberJoinRequestDto {
    private String name;
    private String password;
    private Integer studentNumber;
    private String email;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .password(password)
                .studentNumber(studentNumber)
                .email(email)
                .build();
    }

    @Builder
    public MemberJoinRequestDto(String name, String password,
                                Integer studentNumber, String email) {
        this.name = name;
        this.password = password;
        this.studentNumber = studentNumber;
        this.email = email;
    }
}
