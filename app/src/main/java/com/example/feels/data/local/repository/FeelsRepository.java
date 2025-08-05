package com.example.feels.data.local.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.feels.data.local.JournalDatabase;
import com.example.feels.data.local.dao.JournalDao;
import com.example.feels.data.local.dao.User_Dao;
import com.example.feels.data.local.entities.JournalEntry;
import com.example.feels.data.local.entities.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeelsRepository {
    private final JournalDao journalDao;
    private final User_Dao userDao;
    private final Executor executor;

    public FeelsRepository(Application application) {
        JournalDatabase database = JournalDatabase.getInstance(application);
        journalDao = database.journalDao();
        userDao = database.userDao();
        executor = Executors.newSingleThreadExecutor();
    }

    // Journal operations
    public void insert(JournalEntry entry, OnInsertCompleteListener listener) {
        executor.execute(() -> {
            long id = journalDao.insert(entry);
            listener.onComplete(id);
        });
    }

    public LiveData<List<JournalEntry>> getAllEntries(int userId) {
        return journalDao.getAllEntries(userId);
    }

    public LiveData<List<JournalEntry>> getEntriesForDay(Date date, int userId) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 1);
        Date end = cal.getTime();

        return journalDao.getEntriesByDate(start, end, userId);
    }

    public LiveData<Integer> getTotalEntries(int userId) {
        return journalDao.getTotalEntriesCount(userId);
    }

    public LiveData<Integer> getThisWeekEntriesCount(int userId) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date start = cal.getTime();

        cal.add(Calendar.DATE, 6);
        Date end = cal.getTime();

        return journalDao.getWeeklyEntriesCount(start, end, userId);
    }

    public void deleteAllEntries() {
        executor.execute(journalDao::deleteAll);
    }

    // User operations
    public void insertUser(User user, OnInsertCompleteListener listener) {
        executor.execute(() -> {
            long id = userDao.insert(user);
            listener.onComplete(id);
        });
    }

    public void emailExists(String email, OnEmailCheckListener listener) {
        executor.execute(() -> {
            int count = userDao.emailExists(email);
            listener.onResult(count > 0);
        });
    }

    public void getUserByEmail(String email, OnUserFetchListener listener) {
        executor.execute(() -> {
            User user = userDao.getUserByEmail(email);
            listener.onUserFetched(user);
        });
    }

    // Interfaces
    public interface OnInsertCompleteListener {
        void onComplete(long id);
    }

    public interface OnEmailCheckListener {
        void onResult(boolean exists);
    }

    public interface OnUserFetchListener {
        void onUserFetched(User user);
    }
}