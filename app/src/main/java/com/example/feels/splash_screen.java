package com.example.feels;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        EdgeToEdge.enable(this);

        final View dot = findViewById(R.id.dot);

        dot.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            GradientDrawable background = (GradientDrawable) dot.getBackground();

            dot.animate()
                    .translationYBy(-100f) // Bounce up
                    .setDuration(800)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(() -> {
                        dot.animate()
                                .translationYBy(100f) // Bounce down
                                .setDuration(800)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .withEndAction(() -> {
                                    dot.animate()
                                            .scaleX(100f)
                                            .scaleY(100f)
                                            .setDuration(1100)
                                            .setInterpolator(new AccelerateDecelerateInterpolator())
                                            .withStartAction(() -> {
                                                ValueAnimator colorAnim = ValueAnimator.ofObject(
                                                        new ArgbEvaluator(),
                                                        Color.parseColor("#71DD3E"),
                                                        Color.WHITE
                                                );
                                                colorAnim.setDuration(1200);
                                                colorAnim.addUpdateListener(animation -> {
                                                    int color = (int) animation.getAnimatedValue();
                                                    background.setColor(color);
                                                });
                                                colorAnim.start();
                                            })
                                            .withEndAction(() -> {
                                                // Navigate directly to MainActivity
                                                startActivity(new Intent(splash_screen.this, Notebook.class));
                                                finish();
                                            })
                                            .start();
                                })
                                .start();
                    })
                    .start();
        });
    }
}