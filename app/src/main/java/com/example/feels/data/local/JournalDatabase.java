package com.example.feels.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.feels.data.local.converters.CategoryConverter;
import com.example.feels.data.local.converters.DateConverter;
import com.example.feels.data.local.converters.MoodConverter;
import com.example.feels.data.local.dao.JournalDao;
import com.example.feels.data.local.dao.User_Dao;
import com.example.feels.data.local.entities.JournalEntry;
import com.example.feels.data.local.entities.User;

@Database(
        entities = {JournalEntry.class, User.class}, // Added User entity
        version = 2, // Incremented version
        exportSchema = false
)
@TypeConverters({DateConverter.class, MoodConverter.class, CategoryConverter.class})
public abstract class JournalDatabase extends RoomDatabase {

    private static volatile JournalDatabase INSTANCE;

    public abstract JournalDao journalDao();
    public abstract User_Dao userDao(); // Added User DAO

    // Migration from version 1 to 2
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create users table
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "email TEXT NOT NULL, " +
                            "passwordHash TEXT NOT NULL, " +
                            "created_at INTEGER NOT NULL, " +
                            "UNIQUE(email)" +
                            ")"
            );

            // Add user_id column to journal_entries for relationship
            database.execSQL(
                    "ALTER TABLE journal_entries " +
                            "ADD COLUMN user_id INTEGER NOT NULL DEFAULT 1"
            );
        }
    };

    public static synchronized JournalDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            JournalDatabase.class,
                            "journal_db"
                    )
                    .addMigrations(MIGRATION_1_2) // Added migration
                    .fallbackToDestructiveMigration() // Only for development
                    .build();
        }
        return INSTANCE;
    }

    // Optional: Pre-populate database with test user
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // This runs on a background thread
            new Thread(() -> {
                User_Dao userDao = INSTANCE.userDao();
                User defaultUser = new User("test@example.com",
                        "$2a$12$somehashedpassword"); // Example hashed password
                userDao.insert(defaultUser);
            }).start();
        }
    };
}