package com.example.feels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Color;

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
                                                // ✅ Now check login state *after* animation finishes
                                                SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                                boolean isLoggedIn = false;

                                                if (isLoggedIn) {
                                                    startActivity(new Intent(this, MainActivity.class));
                                                } else {
                                                    startActivity(new Intent(this, Feelings_tracker.class));
                                                }

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
