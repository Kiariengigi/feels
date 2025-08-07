package com.example.feels;

public class CarouselItem {
    private final String title;
    private final String subtitle;
    private final String description;

    public CarouselItem(String title, String subtitle, String description) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getDescription() {
        return description;
    }
}