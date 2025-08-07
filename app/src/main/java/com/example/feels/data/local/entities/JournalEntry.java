package com.example.feels.data.local.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.feels.data.local.converters.CategoryConverter;
import com.example.feels.data.local.converters.DateConverter;
import com.example.feels.data.local.converters.MoodConverter;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "journal_entries")
@TypeConverters({DateConverter.class, MoodConverter.class, CategoryConverter.class})
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    @ColumnInfo(name = "content")
    @NonNull
    private String content;

    @ColumnInfo(name = "date")
    @NonNull
    private Date date;

    @ColumnInfo(name = "mood")
    @NonNull
    private Mood mood;

    @ColumnInfo(name = "category")
    @NonNull
    private Category category;

    // Updated constructor without user ID
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

    // Getters and Setters (no user ID)
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

    public String getDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    public String getMoodAsString() {
        return mood.toString().replace("_", " ");
    }
}