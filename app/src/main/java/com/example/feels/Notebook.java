package com.example.feels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.feels.data.local.entities.Category;
import com.example.feels.data.local.entities.JournalEntry;
import com.example.feels.data.local.entities.Mood;
import com.example.feels.data.local.repository.FeelsRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notebook extends AppCompatActivity {

    private FeelsRepository repository;
    private Mood selectedMood;
    private Category selectedCategory = Category.General; // Default category

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notebook);
        EdgeToEdge.enable(this);

        repository = new FeelsRepository(getApplication());

        // Get mood from intent
        String sentiment = getIntent().getStringExtra("selected_sentiment");
        selectedMood = mapStringToMood(sentiment);

        // Set UI elements
        TextView sentimentView = findViewById(R.id.emotion);
        TextView dayView = findViewById(R.id.Day);
        TextView dateView = findViewById(R.id.short_date);

        sentimentView.setText(sentiment);
        dayView.setText(getTodayDate_short());
        dateView.setText(getTodayDate());

        // Setup category spinner
        Spinner categorySpinner = findViewById(R.id.category_spinner);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Category.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = (Category) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = Category.General;
            }
        });

        // Submit button handler
        Button submitButton = findViewById(R.id.Submit_btn);
        submitButton.setOnClickListener(v -> saveJournalEntry());
    }

    private Mood mapStringToMood(String sentiment) {
        if (sentiment == null) return Mood.Neutral;
        switch (sentiment) {
            case "Very Negative": return Mood.Very_Negative;
            case "Negative": return Mood.Negative;
            case "Neutral": return Mood.Neutral;
            case "Positive": return Mood.Positive;
            case "Very Positive": return Mood.Very_Positive;
            default: return Mood.Neutral;
        }
    }

    private void saveJournalEntry() {
        EditText contentEditText = findViewById(R.id.editTextTextMultiLine);
        String content = contentEditText.getText().toString();
        Date currentDate = new Date();

        JournalEntry entry = new JournalEntry(
                "Journal Entry", // Default title
                content,
                currentDate,
                selectedMood,
                selectedCategory
        );

        repository.insert(entry, id -> {
            runOnUiThread(() -> {
                startActivity(new Intent(Notebook.this, MainActivity.class));
                finish();
            });
        });
    }

    private String getTodayDate_short() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(new Date());
    }

    private String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");
        return sdf.format(new Date());
    }
}