package com.example.feels;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Feelings_tracker extends AppCompatActivity {

    private SeekBar sentimentSeekBar;
    private CircleView blurrycircles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.feelings_tracker);

        sentimentSeekBar = findViewById(R.id.sentimentSeekBar);
        blurrycircles = findViewById(R.id.blurryCircles);

        sentimentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int color = getColorFromProgress(progress);  // You’ll define this method
                blurrycircles.setColorofAllCircles(color);  // Add this method in CircleView
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {
                blurrycircles.setSpeedMultiplier(4.0f);
            }
            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                blurrycircles.setSpeedMultiplier(1.0f);
            }
        });
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

}
