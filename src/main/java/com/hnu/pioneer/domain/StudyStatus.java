package com.hnu.pioneer.domain;

public enum StudyStatus {
    INCRUIT(0),
    ING(1),
    END(2);

    private Integer status;

    StudyStatus(Integer status) {
        this.status = status;
    }
}
