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
    private final JournalDao JournalDao;
    private final Executor executor;

    public FeelsRepository(Application application) {
        JournalDatabase database = JournalDatabase.getInstance(application);
        JournalDao = database.journalDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Insert with callback
    public void insert(JournalEntry entry, OnInsertCompleteListener listener) {
        executor.execute(() -> {
            long id = JournalDao.insert(entry);
            listener.onComplete(id);
        });
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return JournalDao.getAllEntries();
    }

    public LiveData<List<JournalEntry>> getEntriesForDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 1);
        Date end = cal.getTime();

        return JournalDao.getEntriesByDate(start, end);
    }

    public LiveData<Integer> getTotalEntries() {
        return JournalDao.getTotalEntriesCount();
    }

    public LiveData<Integer> getThisWeekEntriesCount() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 6);
        Date end = cal.getTime();

        return JournalDao.getWeeklyEntriesCount(start, end);
    }

    public void deleteAllEntries() {
        executor.execute(JournalDao::deleteAll);
    }

    public interface OnInsertCompleteListener {
        void onComplete(long id);
    }
}