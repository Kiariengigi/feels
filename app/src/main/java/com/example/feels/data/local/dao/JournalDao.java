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

    @Query("SELECT * FROM journal_entries WHERE user_id = :userId")
    LiveData<List<JournalEntry>> getAllEntries(int userId);

    @Query("SELECT * FROM journal_entries WHERE user_id = :userId AND date BETWEEN :start AND :end")
    LiveData<List<JournalEntry>> getEntriesByDate(Date start, Date end, int userId);

    @Query("SELECT COUNT(*) FROM journal_entries WHERE user_id = :userId")
    LiveData<Integer> getTotalEntriesCount(int userId);

    @Query("SELECT COUNT(*) FROM journal_entries WHERE user_id = :userId AND date BETWEEN :start AND :end")
    LiveData<Integer> getWeeklyEntriesCount(Date start, Date end, int userId);

    @Query("DELETE FROM journal_entries")
    void deleteAll();
}