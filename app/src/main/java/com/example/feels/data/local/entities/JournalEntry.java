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

    @ColumnInfo(name = "user_id")
    private int userId;  // Added for user-specific entries

    // Updated constructor
    public JournalEntry(
            @NonNull String title,
            @NonNull String content,
            @NonNull Date date,
            @NonNull Mood mood,
            @NonNull Category category,
            int userId
    ) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.mood = mood;
        this.category = category;
        this.userId = userId;
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

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

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