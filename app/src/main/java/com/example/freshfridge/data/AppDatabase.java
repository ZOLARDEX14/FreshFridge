package com.example.freshfridge.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// ✅ แก้ version เป็น 2 เพื่อให้ Room สร้าง schema ใหม่ (ไม่เด้ง)
@Database(entities = {FoodItem.class, MealPlan.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract FoodItemDao foodItemDao();
    public abstract MealPlanDao mealPlanDao();

    // ✅ Executor สำหรับทำงาน background (insert/delete)
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "food_database"
                            )
                            // ✅ สำคัญ! แก้ปัญหา schema mismatch crash
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
