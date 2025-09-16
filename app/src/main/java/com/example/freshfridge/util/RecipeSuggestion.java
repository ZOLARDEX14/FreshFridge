package com.example.freshfridge.util;

import com.example.freshfridge.data.FoodItem;

import java.util.List;

public class RecipeSuggestion {

    public static String suggest(List<FoodItem> items) {
        boolean hasChicken = false;
        boolean hasBell = false;

        for (FoodItem item : items) {
            if (item.name.toLowerCase().contains("ไก่")) hasChicken = true;
            if (item.name.toLowerCase().contains("พริกหยวก")) hasBell = true;
        }

        if (hasChicken && hasBell) return "ไก่ผัดพริกหยวก";
        else if (hasChicken) return "ต้มไก่ใส่เกลือ";
        else if (hasBell) return "พริกหยวกผัดไข่";
        else return "ไม่มีเมนูแนะนำ";
    }
}
