package com.hnu.pioneer.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StudyStatus {
    INCRUIT("모집중"),
    OPEN("진행중"),
    CLOSED("종료");

    private final String status;
}
