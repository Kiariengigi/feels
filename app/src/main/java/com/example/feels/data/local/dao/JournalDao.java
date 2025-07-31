package com.example.feels.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.feels.data.local.entities.JournalEntry;
import com.example.feels.data.local.entities.Mood;

import java.util.Date;
import java.util.List;

@Dao
public interface JournalDao {
    // CRUD Operations
    @Insert
    long insert(JournalEntry entry);

    @Update
    int update(JournalEntry entry);

    @Delete
    void delete(JournalEntry entry);

    // Query: Get all entries sorted by date
    @Query("SELECT * FROM journal_entries ORDER BY entry_date DESC")
    LiveData<List<JournalEntry>> getAllEntries();

    // Query: Get entries for specific date
    @Query("SELECT * FROM journal_entries WHERE entry_date BETWEEN :start AND :end")
    LiveData<List<JournalEntry>> getEntriesByDate(Date start, Date end);

    // Query: Get entries by mood
    @Query("SELECT * FROM journal_entries WHERE mood = :mood")
    LiveData<List<JournalEntry>> getEntriesByMood(Mood mood);

    // Query: Get total entry count
    @Query("SELECT COUNT(*) FROM journal_entries")
    LiveData<Integer> getTotalEntriesCount();

    // Query: Get weekly entry count
    @Query("SELECT COUNT(*) FROM journal_entries " +
            "WHERE entry_date BETWEEN :startOfWeek AND :endOfWeek")
    LiveData<Integer> getWeeklyEntriesCount(Date startOfWeek, Date endOfWeek);

    // Clear all entries
    @Query("DELETE FROM journal_entries")
    void deleteAll();
}