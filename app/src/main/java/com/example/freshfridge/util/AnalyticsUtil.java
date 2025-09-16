package com.example.freshfridge.util;

import com.example.freshfridge.data.FoodItem;

import java.util.List;

public class AnalyticsUtil {

    public static String analyzeIntake(List<FoodItem> items) {
        int meat = 0, veg = 0, dairy = 0;

        for (FoodItem item : items) {
            switch (item.category) {
                case "meat":
                    meat++;
                    break;
                case "vegetable":
                    veg++;
                    break;
                case "dairy":
                    dairy++;
                    break;
            }
        }

        return "เดือนนี้คุณกิน:\n" +
                "🥩 เนื้อสัตว์: " + meat + " ครั้ง\n" +
                "🥬 ผักและผลไม้: " + veg + " ครั้ง\n" +
                "🥛 ผลิตภัณฑ์นม: " + dairy + " ครั้ง";
    }
}
