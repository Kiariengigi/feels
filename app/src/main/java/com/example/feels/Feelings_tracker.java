package com.example.feels;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Feelings_tracker extends AppCompatActivity {

    private SeekBar sentimentSeekBar;
    private TextView sentimentValue;
    private CircleView blurrycircles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.feelings_tracker);

        sentimentSeekBar = findViewById(R.id.sentimentSeekBar);
        sentimentValue = findViewById(R.id.sentimentValue);
        blurrycircles = findViewById(R.id.blurryCircles);

        sentimentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int color = getColorFromProgress(progress);  // You’ll define this method
                blurrycircles.setColorofAllCircles(color);  // Add this method in CircleView
                String sentiment = getSentimentLabel(progress);
                sentimentValue.setText(sentiment);
                sentimentSeekBar.setProgressDrawable(dynamicGradientTrack(color));
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {
                blurrycircles.setSpeedMultiplier(4.0f);
            }
            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                blurrycircles.setSpeedMultiplier(1.0f);
            }
        });

        Button submitBtn = findViewById(R.id.button2);
        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int progress = sentimentSeekBar.getProgress();
                String sentiment = getSentimentLabel(progress);

                Intent intent = new Intent(Feelings_tracker.this, Notebook.class);
                intent.putExtra("selected_sentiment", sentiment);
                startActivity(intent);
            }
        });
    }



    private String getSentimentLabel(int progress){
        if (progress < 25) return "Very Negative";
        else if (progress < 50) return "Negative";
        else if (progress < 75) return "Neutral";
        else if (progress < 100) return "Positive";
        else return "Very Positive";
    }
    private int getColorFromProgress(int progress) {
        // Simple gradient: Blue (0) → Green (50) → Red (100)
        if (progress <= 50) {
            // Blue to Green
            int blue = 255 - (progress * 5);   // from 255 to 5
            int green = progress * 5;          // from 0 to 255
            return android.graphics.Color.rgb(0, green, blue);
        } else {
            // Green to Red
            int red = (progress - 50) * 5;     // from 0 to 250
            int green = 255 - (progress - 50) * 5; // from 255 to 5
            return android.graphics.Color.rgb(red, green, 0);
        }
    }

    private Drawable dynamicGradientTrack(int basecolor){
        int[] colors = new int[]{0xFFB0E0E6, basecolor};
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, colors);
        gradient.setCornerRadius(4 * getResources().getDisplayMetrics().density);
        gradient.setSize(400, 20);
        return gradient;
    }


}
