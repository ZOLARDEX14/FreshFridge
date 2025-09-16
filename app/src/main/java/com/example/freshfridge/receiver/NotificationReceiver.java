package com.example.freshfridge.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.freshfridge.R;

import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "food_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("ของใกล้หมดอายุ")
                .setContentText("อย่าลืมกิน " + name + " ก่อนหมดอายุนะ!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat.from(context).notify(new Random().nextInt(), builder.build());
    }
}
