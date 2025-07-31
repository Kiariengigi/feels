package com.example.feels.data.local.entities;

public enum Category {
    PERSONAL,
    WORK,
    HEALTH,
    RELATIONSHIPS,
    GOALS,
    GRATITUDE;

    public static Category fromString(String value) {
        return valueOf(value.toUpperCase());
    }
}