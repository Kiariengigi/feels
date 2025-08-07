package com.example.feels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.feels.data.local.entities.JournalEntry;
import com.example.feels.data.local.repository.FeelsRepository;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private final FeelsRepository repository;
    private final MediatorLiveData<List<JournalEntry>> allEntries = new MediatorLiveData<>();
    private LiveData<List<JournalEntry>> repositoryLiveData;

    public EntryViewModel(Application application) {
        super(application);
        repository = new FeelsRepository(application);

        // Get LiveData from repository
        repositoryLiveData = repository.getAllEntries();

        // Use MediatorLiveData to observe the repository's LiveData
        allEntries.addSource(repositoryLiveData, entries -> {
            allEntries.setValue(entries);
        });
    }

    public LiveData<List<JournalEntry>> getAllEntries() {
        return allEntries;
    }

    public void refreshData() {
        // Since we're using LiveData, it automatically updates
        // But we can re-establish the connection if needed
        if (repositoryLiveData != null) {
            allEntries.removeSource(repositoryLiveData);
        }
        repositoryLiveData = repository.getAllEntries();
        allEntries.addSource(repositoryLiveData, entries -> {
            allEntries.setValue(entries);
        });
    }
}