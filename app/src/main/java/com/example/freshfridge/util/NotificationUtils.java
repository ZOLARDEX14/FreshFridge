package com.example.freshfridge.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.freshfridge.data.FoodItem;
import com.example.freshfridge.receiver.NotificationReceiver;

public class NotificationUtils {

    public static void scheduleNotification(Context context, FoodItem item) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("name", item.name);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                item.id,  // ใช้ id จาก Room เป็น requestCode
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // กำหนดเวลาแจ้งเตือน: 1 วันก่อนหมดอายุ
        long time = item.expiryDate - 86400000L;

        if (alarmManager != null && time > System.currentTimeMillis()) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
    }
}
