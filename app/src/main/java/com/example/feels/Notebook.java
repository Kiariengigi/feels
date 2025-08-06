package com.example.feels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Notebook extends AppCompatActivity {

    private ConstraintLayout rootLayout;
    private View dot;
    private TextView emotion;
    public String getTodayDate_short() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date now = new Date();
        return sdf.format(now);
    }

    public String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd");
        Date now = new Date();
        return sdf.format(now);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notebook);

        Button button = findViewById(R.id.Submit_btn);
        dot = findViewById(R.id.dot);
        rootLayout = findViewById(R.id.main);
        emotion = findViewById(R.id.emotion);


        // Set sentiment
        String sentiment = getIntent().getStringExtra("selected_sentiment");
        TextView sentimentView = findViewById(R.id.emotion);
        sentimentView.setText(sentiment);

        // Set date
        TextView day = findViewById(R.id.Day);
        TextView long_day = findViewById(R.id.short_date);
        day.setText(getTodayDate_short());
        long_day.setText(getTodayDate());

        if (Objects.equals(sentiment, "Very Negative")) {
            button.setBackgroundColor(Color.parseColor("#FF1744"));
            emotion.setTextColor(Color.parseColor("#FF1744"));
            dot.setBackgroundColor(Color.parseColor("#FF1744"));
            rootLayout.setBackgroundColor(Color.parseColor("#E7D3D3"));
        } else if (Objects.equals(sentiment, "Negative")) {
            button.setBackgroundColor(Color.parseColor("#536DFE"));
            dot.setBackgroundColor(Color.parseColor("#536DFE"));
            rootLayout.setBackgroundColor(Color.parseColor("#E7D3D3"));
            emotion.setTextColor(Color.parseColor("#536DFE"));
        } else if (Objects.equals(sentiment, "Neutral")) {
            button.setBackgroundColor(Color.parseColor("#B0BEC5"));
            dot.setBackgroundColor(Color.parseColor("#B0BEC5"));
            rootLayout.setBackgroundColor(Color.parseColor("#EEEEEE"));
            emotion.setTextColor(Color.parseColor("#B0BEC5"));
        } else if (Objects.equals(sentiment, "Positive")) {
            button.setBackgroundColor(Color.parseColor("#FFD600"));
            dot.setBackgroundColor(Color.parseColor("#FFD600"));
            rootLayout.setBackgroundColor(Color.parseColor("#DBE4C9"));
            emotion.setTextColor(Color.parseColor("#FFD600"));
        } else {
            button.setBackgroundColor(Color.parseColor("#00E676"));
            dot.setBackgroundColor(Color.parseColor("#00E676"));
            rootLayout.setBackgroundColor(Color.parseColor("#FEFFC4"));
            emotion.setTextColor(Color.parseColor("#00E676"));
        }

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
