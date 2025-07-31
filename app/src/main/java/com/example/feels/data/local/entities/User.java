package com.example.feels.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String email;

    private String passwordHash;
    private Date createdAt; // Using Date instead of long

    // Constructor
    public User(@NonNull String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = new Date(); // Auto-set to current time
    }

    // Getters
    public int getId() { return id; }

    @NonNull
    public String getEmail() { return email; }

    public String getPasswordHash() { return passwordHash; }

    public Date getCreatedAt() { return createdAt; }

    // Setters (Required by Room)
    public void setId(int id) { this.id = id; }

    public void setEmail(@NonNull String email) { this.email = email; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}