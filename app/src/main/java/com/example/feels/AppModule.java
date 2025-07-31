package com.example.feels;

import android.app.Application;

import com.example.feels.data.local.JournalDatabase;
import com.example.feels.data.local.dao.JournalDao;
import com.example.feels.data.local.repository.FeelsRepository;
import com.example.feels.data.local.repository.FeelsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {
    @Provides
    @Singleton
    public static JournalDatabase provideDatabase(Application app) {
        return JournalDatabase.getInstance(app);
    }

    @Provides
    @Singleton
    public static JournalDao provideJournalDao(JournalDatabase db) {
        return db.journalDao();
    }

    @Provides
    @Singleton
    public static FeelsRepository provideRepository(
            Application app,
            JournalDao dao
    ) {
        return new FeelsRepository(app);
    }
}