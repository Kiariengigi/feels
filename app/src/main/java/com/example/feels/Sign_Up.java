package com.example.feels;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.feels.data.local.entities.User;
import com.example.feels.data.local.repository.FeelsRepository;
import com.example.feels.PasswordUtils;
import com.example.feels.SessionManager;

public class Sign_Up extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private FeelsRepository feelsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        feelsRepository = new FeelsRepository(getApplication());
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText2);
        confirmPasswordEditText = findViewById(R.id.passwordEditText);

        // Sign in link
        TextView textView = findViewById(R.id.login_acc);
        String fullText = "Already have an account? Sign in";
        SpannableString spannable = new SpannableString(fullText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(Sign_Up.this, Login.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor("#FF4081"));
                ds.setUnderlineText(false);
            }
        };

        int startIndex = fullText.indexOf("Sign in");
        spannable.setSpan(clickableSpan, startIndex, startIndex + "Sign in".length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        // Signup button
        //findViewById(R.id.button).setOnClickListener(v -> handleSignup());
    }

    private void handleSignup() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        feelsRepository.emailExists(email, exists -> {
            if (exists) {
                runOnUiThread(() ->
                        Toast.makeText(Sign_Up.this, "Email already registered", Toast.LENGTH_SHORT).show());
            } else {
                String hashedPassword = PasswordUtils.hashPassword(password);
                User user = new User(email, hashedPassword);

                feelsRepository.insertUser(user, userId -> {
                    if (userId > 0) {
                        SessionManager.saveUserSession(Sign_Up.this, (int) userId, email);
                        startActivity(new Intent(Sign_Up.this, MainActivity.class));
                        finish();
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(Sign_Up.this, "Registration failed", Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
    }
}