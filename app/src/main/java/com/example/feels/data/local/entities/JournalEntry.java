package com.example.feels.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "journal_entries")
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "content")
    @NonNull
    private String content;

    @ColumnInfo(name = "entry_date")
    @NonNull
    private Date date;

    @ColumnInfo(name = "mood")
    @NonNull
    private Mood mood;

    @ColumnInfo(name = "category")
    @NonNull
    private Category category;

    // Constructor
    public JournalEntry(
            @NonNull String title,
            @NonNull String content,
            @NonNull Date date,
            @NonNull Mood mood,
            @NonNull Category category
    ) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.mood = mood;
        this.category = category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getTitle() { return title; }

    @NonNull
    public String getContent() { return content; }

    @NonNull
    public Mood getMood() { return mood; }

    @NonNull
    public Category getCategory() { return category; }

    @NonNull
    public Date getDate() { return date; }

    // Helper Methods
    public String getMoodAsString() {
        return mood != null ? mood.toString() : "Unknown";
    }

    public String getCategoryAsString() {
        return category != null ? category.toString() : "Unknown";
    }

    public String getDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}
