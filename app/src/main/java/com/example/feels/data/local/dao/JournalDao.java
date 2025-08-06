package com.example.feels.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.feels.data.local.entities.JournalEntry;

import java.util.Date;
import java.util.List;

@Dao
public interface JournalDao {
    @Insert
    long insert(JournalEntry entry);

    @Query("SELECT * FROM journal_entries") // Removed user_id filter
    LiveData<List<JournalEntry>> getAllEntries();

    @Query("SELECT * FROM journal_entries WHERE date BETWEEN :start AND :end") // Removed user_id
    LiveData<List<JournalEntry>> getEntriesByDate(Date start, Date end);

    @Query("SELECT COUNT(*) FROM journal_entries") // Removed user_id
    LiveData<Integer> getTotalEntriesCount();

    @Query("SELECT COUNT(*) FROM journal_entries WHERE date BETWEEN :start AND :end") // Removed user_id
    LiveData<Integer> getWeeklyEntriesCount(Date start, Date end);

    @Query("DELETE FROM journal_entries")
    void deleteAll();
}