package com.hnu.pioneer.Dto;

import com.hnu.pioneer.domain.Member;
import com.hnu.pioneer.domain.Role;
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
    private Role role;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .password(password)
                .studentNumber(studentNumber)
                .email(email)
                .role(role)
                .build();
    }

    @Builder
    public MemberSaveRequestDto(String name, String password,
                                Long studentNumber, String email, Role role) {
        this.name = name;
        this.password = password;
        this.studentNumber = studentNumber;
        this.email = email;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
