package com.example.feels.data.local.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.feels.data.local.JournalDatabase;
import com.example.feels.data.local.dao.JournalDao;
import com.example.feels.data.local.entities.JournalEntry;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeelsRepository {
    private final JournalDao journalDao;
    private final Executor executor;

    public FeelsRepository(Application application) {
        JournalDatabase database = JournalDatabase.getInstance(application);
        journalDao = database.journalDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Journal operations (no user ID)
    public void insert(JournalEntry entry, OnInsertCompleteListener listener) {
        executor.execute(() -> {
            long id = journalDao.insert(entry);
            listener.onComplete(id);
        });
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return journalDao.getAllEntries();
    }

    public LiveData<List<JournalEntry>> getEntriesForDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 1);
        Date end = cal.getTime();

        return journalDao.getEntriesByDate(start, end);
    }

    public LiveData<Integer> getTotalEntries() {
        return journalDao.getTotalEntriesCount();
    }

    public LiveData<Integer> getThisWeekEntriesCount() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 6);
        Date end = cal.getTime();

        return journalDao.getWeeklyEntriesCount(start, end);
    }

    public void deleteAllEntries() {
        executor.execute(journalDao::deleteAll);
    }

    // Interface remains the same
    public interface OnInsertCompleteListener {
        void onComplete(long id);
    }
}