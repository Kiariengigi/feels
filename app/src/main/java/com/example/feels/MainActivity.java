package com.example.feels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
            String title = date;
            String subtitle = emotion;
            String description = summary;
            itemList.add(new CarouselItem(title, subtitle, description)); // Use a default icon or one passed via intent
        } else {
            itemList.add(new CarouselItem("No journal entries yet","No subtitle", "No description"));
        }

        // 👉 Set adapter
        CarouselAdapter adapter = new CarouselAdapter(this, itemList); // 'this' is the context
        recyclerView.setAdapter(adapter);

        // 👉 Snap scrolling behavior
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        Button button3 = findViewById(R.id.button2);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Feelings_tracker.class);
                startActivity(intent);
            }
        });
    }

}

