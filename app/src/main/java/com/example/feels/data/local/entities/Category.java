package com.example.feels.data.local.entities;

public enum Category {
    General("General"),
    Work("Work"),
    Personal("Personal"),
    Health("Health"),
    Relationships("Relationships"),
    Hobbies("Hobbies");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}