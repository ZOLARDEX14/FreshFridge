package com.example.freshfridge.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_plan_table") // ✅ ชื่อตรงกับ DAO
public class MealPlan {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String date;  // รูปแบบ: yyyy-MM-dd

    @NonNull
    public String meal;  // เช่น "ผัดพริกไก่"

    public MealPlan(@NonNull String date, @NonNull String meal) {
        this.date = date;
        this.meal = meal;
    }
}
