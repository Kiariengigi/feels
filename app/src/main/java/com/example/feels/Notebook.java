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

    private Mood selectedMood;
    private Category selectedCategory = Category.General;
    private FeelsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notebook);

        // Initialize repository
        repository = new FeelsRepository(getApplication());

        // Get sentiment from intent
        String sentiment = getIntent().getStringExtra("selected_sentiment");
        selectedMood = mapStringToMood(sentiment);

        // Set UI elements
        TextView sentimentView = findViewById(R.id.emotion);
        TextView dayView = findViewById(R.id.Day);
        TextView dateView = findViewById(R.id.short_date);

        sentimentView.setText(sentiment);
        dayView.setText(getTodayDate_short());
        dateView.setText(getTodayDate());




        // Set button colors based on mood
        Button submitButton = findViewById(R.id.button2);
        Button smButton = findViewById(R.id.button3);
        setButtonColors(submitButton, smButton, sentimentView, sentiment);

        // Save to database on submit
        submitButton.setOnClickListener(v -> saveJournalEntry());
    }

    private void setButtonColors(Button submitButton, Button smButton,
                                 TextView sentimentView, String sentiment) {
        int colorRes = getColorForMood(sentiment);
        int color = getResources().getColor(colorRes);

        submitButton.setBackgroundColor(color);
        sentimentView.setTextColor(color);
        smButton.setBackgroundColor(color);
    }

    private int getColorForMood(String sentiment) {
        if (sentiment == null) return R.color.default_color;

        switch (sentiment) {
            case "Very Negative": return R.color.very_negative_color;
            case "Negative": return R.color.negative_color;
            case "Neutral": return R.color.neutral_color;
            case "Positive": return R.color.positive_color;
            case "Very Positive": return R.color.very_positive_color;
            default: return R.color.default_color;
        }
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

        // Create journal entry
        JournalEntry entry = new JournalEntry(
                "Journal Entry", // Default title
                content,
                currentDate,
                selectedMood,
                selectedCategory
        );

        // Save to database
        repository.insert(entry, id -> {
            // Navigate back to MainActivity after save
            runOnUiThread(() -> {
                startActivity(new Intent(Notebook.this, MainActivity.class));
                finish();
            });
        });
    }

    private String getTodayDate_short() {
        return new SimpleDateFormat("EEEE").format(new Date());
    }

    private String getTodayDate() {
        return new SimpleDateFormat("MMMM dd yyyy").format(new Date());
    }
}