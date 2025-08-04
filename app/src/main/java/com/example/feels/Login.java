package com.example.feels;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_screen);

        // Set up clickable "Sign up" text
        TextView textView = findViewById(R.id.login_acc);
        String fullText = "Don’t have an account? Sign up";
        SpannableString spannable = new SpannableString(fullText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(Login.this, Sign_Up.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#FF4081")); // pink color
                ds.setUnderlineText(false);
            }
        };

        int startIndex = fullText.indexOf("Sign up");
        int endIndex = startIndex + "Sign up".length();
        spannable.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        // Handle login button
        Button openSecondButton = findViewById(R.id.log_in_btn);
        openSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set shared preference as logged in
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                // Go to next activity
                Intent intent = new Intent(Login.this, Feelings_tracker.class);
                startActivity(intent);
            }
        });
    }
}
