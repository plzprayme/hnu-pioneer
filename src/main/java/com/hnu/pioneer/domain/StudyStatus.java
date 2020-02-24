package com.hnu.pioneer.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StudyStatus {
    INCRUIT("모집중"),
    ING("진행중"),
    END("종료");

    private final String status;
}
