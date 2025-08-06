package com.example.feels.data.local;

import android.content.Context;

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
import com.example.feels.data.local.entities.JournalEntry;

@Database(
        entities = {JournalEntry.class}, // Removed User entity
        version = 2, // Keep version or increment if needed
        exportSchema = false
)
@TypeConverters({DateConverter.class, MoodConverter.class, CategoryConverter.class})
public abstract class JournalDatabase extends RoomDatabase {

    private static volatile JournalDatabase INSTANCE;

    public abstract JournalDao journalDao();
    // Removed userDao()

    // Updated migration (remove user table creation)
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Only keep journal table modifications
            // Removed user table creation
        }
    };

    public static synchronized JournalDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            JournalDatabase.class,
                            "journal_db"
                    )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    // Remove roomCallback (user pre-population)
}