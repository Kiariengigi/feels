package com.example.feels.data.local.converters;

import androidx.room.TypeConverter;

import com.example.feels.data.local.entities.Category;

public class CategoryConverter {
    @TypeConverter
    public static Category toCategory(String value) {
        return value == null ? null : Category.valueOf(value);
    }

    @TypeConverter
    public static String fromCategory(Category category) {
        return category == null ? null : category.name();
    }
}