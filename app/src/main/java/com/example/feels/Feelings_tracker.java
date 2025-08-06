package com.example.feels;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class Feelings_tracker extends AppCompatActivity {

    private SeekBar sentimentSeekBar;
    private TextView sentimentValue;
    private TextView toptext;
    private TextView labelNegative;
    private TextView labelPositive;
    private CircleView blurrycircles;
    private MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.feelings_tracker);

        sentimentSeekBar = findViewById(R.id.sentimentSeekBar);
        toptext = findViewById(R.id.textView3);
        labelNegative = findViewById(R.id.labelNegative);
        labelPositive = findViewById(R.id.labelPositive);
        sentimentValue = findViewById(R.id.sentimentValue);
        blurrycircles = findViewById(R.id.blurryCircles);
        button = findViewById(R.id.myButton); // <-- Fix is here

        sentimentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int index;
                if (progress < 20) index = 0;
                else if (progress < 40) index = 1;
                else if (progress < 60) index = 2;
                else if (progress < 80) index = 3;
                else index = 4;

                sentimentValue.setText(getSentimentLabel(progress));
                int[] themeColors = circleColorThemes[index];
                int circleColor = themeColors[0];
                int newBgColor = backgroundColors[index];

                blurrycircles.setColors(themeColors);
                animateBackgroundColor(newBgColor);
                animateButtonTint(circleColor);
                sentimentValue.setTextColor(circleColor);// animate button with same primary color

                if (index == 0) {
                    // Very Negative selected
                    sentimentValue.setTextColor(Color.WHITE);
                    toptext.setTextColor(Color.WHITE);
                    labelNegative.setTextColor(Color.WHITE);
                    labelPositive.setTextColor(Color.WHITE);
                } else {
                    sentimentValue.setTextColor(Color.BLACK);
                    toptext.setTextColor(Color.BLACK);
                    labelNegative.setTextColor(Color.BLACK);
                    labelPositive.setTextColor(Color.BLACK);
                }


            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        button.setOnClickListener(v -> {
            int progress = sentimentSeekBar.getProgress();
            String sentiment = getSentimentLabel(progress);
            Intent intent = new Intent(Feelings_tracker.this, Notebook.class);
            intent.putExtra("selected_sentiment", sentiment);
            startActivity(intent);
        });
    }


    private void animateButtonTint(int toColor) {
        ColorStateList tintList = button.getBackgroundTintList();
        int currentColor = tintList != null ? tintList.getDefaultColor() : Color.GRAY;

        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), currentColor, toColor);
        animator.setDuration(400);
        animator.addUpdateListener(animation ->
                button.setBackgroundTintList(ColorStateList.valueOf((int) animation.getAnimatedValue())));
        animator.start();
    }

    private String getSentimentLabel(int progress) {
        if (progress < 20) return "Very Negative";
        else if (progress < 40) return "Negative";
        else if (progress < 60) return "Neutral";
        else if (progress < 80) return "Positive";
        else return "Very Positive";
    }

    private void animateBackgroundColor(int toColor) {
        View rootView = findViewById(android.R.id.content);
        Drawable background = rootView.getBackground();

        int fromColor;
        if (background instanceof ColorDrawable) {
            fromColor = ((ColorDrawable) background).getColor();
        } else {
            fromColor = Color.WHITE;
            rootView.setBackgroundColor(fromColor);
        }

        ValueAnimator colorAnim = ValueAnimator.ofArgb(fromColor, toColor);
        colorAnim.setDuration(600);
        colorAnim.addUpdateListener(anim -> rootView.setBackgroundColor((int) anim.getAnimatedValue()));
        colorAnim.start();
    }

    // Color themes
    int[][] circleColorThemes = {
            {Color.parseColor("#FF1744"), Color.parseColor("#D50000"), Color.parseColor("#B71C1C")},  // Very Negative
            {Color.parseColor("#536DFE"), Color.parseColor("#3D5AFE"), Color.parseColor("#1A237E")},  // Negative
            {Color.parseColor("#B0BEC5"), Color.parseColor("#90A4AE"), Color.parseColor("#78909C")},  // Neutral
            {Color.parseColor("#FFD600"), Color.parseColor("#FFAB00"), Color.parseColor("#FFA000")},  // Positive
            {Color.parseColor("#00E676"), Color.parseColor("#1DE9B6"), Color.parseColor("#00BFA5")}   // Very Positive
    };

    int[] backgroundColors = {
            Color.parseColor("#212121"), // Very Negative
            Color.parseColor("#ECEFF1"), // Negative
            Color.parseColor("#F5F5F5"), // Neutral
            Color.parseColor("#FFF8E1"), // Positive
            Color.parseColor("#E0F2F1")  // Very Positive
    };
}
