package com.example.feels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.feels.data.local.entities.JournalEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CarouselAdapter adapter;
    private EntryViewModel viewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.carouselRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Initialize adapter with empty list
        adapter = new CarouselAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Observe LiveData from ViewModel
        viewModel.getAllEntries().observe(this, entries -> {
            Log.d(TAG, "Received entries: " + (entries != null ? entries.size() : 0));

            if (entries != null && !entries.isEmpty()) {
                List<CarouselItem> carouselItems = new ArrayList<>();
                for (JournalEntry entry : entries) {
                    carouselItems.add(new CarouselItem(
                            entry.getDateAsString(),
                            entry.getMoodAsString(),
                            entry.getContent()
                    ));
                }
                adapter.setItems(carouselItems);
                Log.d(TAG, "Updated adapter with " + carouselItems.size() + " items");
            } else {
                Log.d(TAG, "No entries found or entries list is empty");
                // Show empty state or keep empty list
                adapter.setItems(new ArrayList<>());
            }
        });

        // Snap scrolling behavior
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        // Plus button click handler
        Button button3 = findViewById(R.id.button2);
        button3.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Feelings_tracker.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called - refreshing data");
        // Refresh data when returning to this activity
        viewModel.refreshData();
    }
}