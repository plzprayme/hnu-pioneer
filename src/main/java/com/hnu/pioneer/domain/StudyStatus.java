package com.hnu.pioneer.domain;

public enum StudyStatus {
    INCRUIT("모집중"),
    ING("진행중"),
    END("종료");

    private String status;

    StudyStatus(String status) {
        this.status = status;
    }
}
