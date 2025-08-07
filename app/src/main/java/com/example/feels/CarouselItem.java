package com.example.feels;

import android.widget.LinearLayout;

public class CarouselItem {
    private String title;
    private String subtitle;
    private String description;


    public CarouselItem(String title, String subtitle, String description) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;

        if (subtitle == "Very Negative"){

        }
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



