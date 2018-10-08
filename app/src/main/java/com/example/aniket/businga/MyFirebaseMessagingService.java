package com.example.aniket.businga;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    final String TAG="firebase";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        SharedPreferences sharedPreferences = getSharedPreferences("Enable notifications", MODE_PRIVATE);
        boolean enable = sharedPreferences.getBoolean("notifications", true);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "enable notifications  " + enable);
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());



        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null && enable == true) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());


            Intent intent = new Intent(this, HomePage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
            notificationBuilder.setContentTitle("Businga");
            notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
