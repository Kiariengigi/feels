package com.example.feels;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 👉 Always initialize views first
        RecyclerView recyclerView = findViewById(R.id.carouselRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // 👉 Get data from intent
        String date = getIntent().getStringExtra("date");
        String emotion = getIntent().getStringExtra("emotion");
        String summary = getIntent().getStringExtra("essay");

        // 🔁 Build carousel items
        List<CarouselItem> itemList = new ArrayList<>();

        if (date != null && emotion != null && summary != null) {
            String title = date + " - " + emotion + ": " + summary;
            itemList.add(new CarouselItem(title)); // Use a default icon or one passed via intent
        } else {
            itemList.add(new CarouselItem("No journal entries yet"));
        }

        // 👉 Set adapter
        CarouselAdapter adapter = new CarouselAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // 👉 Snap scrolling behavior
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}
