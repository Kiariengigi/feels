package com.example.feels.data.local.entities;

public enum Mood {
    Very_Negative,
    Negative,
    Neutral,
    Positive,
    Very_Positive;

    public static Mood fromString(String value) {
        return valueOf(value.toUpperCase());
    }
    }