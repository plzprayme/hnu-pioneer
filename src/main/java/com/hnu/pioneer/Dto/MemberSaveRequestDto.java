package com.hnu.pioneer.Dto;

import com.hnu.pioneer.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSaveRequestDto {
    private String name;
    private String password;
    private Long studentNumber;
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
    public MemberSaveRequestDto(String name, String password,
                                Long studentNumber, String email) {
        this.name = name;
        this.password = password;
        this.studentNumber = studentNumber;
        this.email = email;
    }
}
