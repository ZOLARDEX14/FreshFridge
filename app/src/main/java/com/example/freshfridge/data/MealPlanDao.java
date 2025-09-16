package com.example.freshfridge.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MealPlanDao {

    // ➕ เพิ่มเมนูลงในแผน
    @Insert
    void insert(MealPlan mealPlan);

    // ❌ ลบเมนู
    @Delete
    void delete(MealPlan mealPlan);

    // 📅 ดึงเมนูตามวันที่
    @Query("SELECT * FROM meal_plan_table WHERE date = :date")
    LiveData<List<MealPlan>> getMealsForDate(String date);

    // 📋 ดึงแผนทั้งหมด (optionally ใช้แสดงย้อนหลัง)
    @Query("SELECT * FROM meal_plan_table ORDER BY date ASC")
    LiveData<List<MealPlan>> getAllPlans();
}
