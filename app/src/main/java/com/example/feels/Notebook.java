package com.example.feels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notebook extends AppCompatActivity {

    public String getTodayDate_short() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date now = new Date();
        return sdf.format(now);
    }

    public String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
        Date now = new Date();
        return sdf.format(now);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notebook);

        // Set sentiment
        String sentiment = getIntent().getStringExtra("selected_sentiment");
        TextView sentimentView = findViewById(R.id.emotion);
        sentimentView.setText(sentiment);

        // Set date
        TextView day = findViewById(R.id.Day);
        TextView long_day = findViewById(R.id.short_date);
        day.setText(getTodayDate_short());
        long_day.setText(getTodayDate());

        // Setup onClick for a button
        Button submitButton = findViewById(R.id.Submit_btn); // Make sure your layout has this ID
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView editText = findViewById(R.id.editTextTextMultiLine);
                TextView editText2 = findViewById(R.id.emotion);
                TextView editText3 = findViewById(R.id.Day);
                TextView editText4 = findViewById(R.id.short_date);
                String essay = editText.getText().toString();
                String emotion = editText2.getText().toString();
                String day = editText3.getText().toString();
                String date = editText4.getText().toString();

                Intent intent = new Intent(Notebook.this, MainActivity.class);
                intent.putExtra("essay", essay);
                intent.putExtra("emotion", emotion);
                intent.putExtra("day", day);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}
