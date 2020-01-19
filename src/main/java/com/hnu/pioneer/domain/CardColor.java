package com.hnu.pioneer.domain;

public enum CardColor {
    PRIMARY("primary"),
    SECONDARY("secondary"),
    SUCCESS("success"),
    DANGER("danger"),
    WARNING("warning"),
    INFO("info"),
    DARK("dark");

    private String color;

    CardColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
