package com.example.reggiewashington.c196;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT;

public class MyReceiver extends BroadcastReceiver{

    static int notificationID, notificationID2;
    String channel_1_id="channel1";
    String channel_2_id="channel2";

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getStringExtra("Title");
        String message = intent.getStringExtra("Message");
        Toast.makeText(context,"Notification",Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_1_id);
        createNotificationChannel(context, channel_2_id);
        Notification n= new NotificationCompat.Builder(context, channel_1_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentTitle(title)
                .build();
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,n);
/*
        Notification n2= new NotificationCompat.Builder(context, channel_2_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText("A term is ending!")
                .setContentTitle("Term Alert")
                .build();
        NotificationManager notificationManager2=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager2.notify(notificationID2,n2);
*/
        //Put a notification her aka Vogella Tutorial

        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
    }
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification channel";
            String description = "notification channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID, name, importance);
            channel1.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);

        }
    }
}
