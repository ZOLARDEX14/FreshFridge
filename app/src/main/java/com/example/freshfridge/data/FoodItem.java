package com.example.freshfridge.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_table")
public class FoodItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    public long expiryDate;

    @NonNull
    public String category;  // เช่น "meat", "vegetable", "dairy"

    // ✅ Constructor หลัก: ใช้ name, date, category
    public FoodItem(@NonNull String name, long expiryDate, @NonNull String category) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    // ✅ Constructor สำรอง: กรณีไม่มี category (ใช้ default = "other")
    public FoodItem(@NonNull String name, long expiryDate) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.category = "other";
    }

    // ✅ Constructor ว่าง: เผื่อ Room หรือ serialization ต้องการ
    public FoodItem() {
        this.name = "";
        this.expiryDate = 0L;
        this.category = "other";
    }
}
