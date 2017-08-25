package com.example.termtraker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    private String s;
    private String alert;
    private int id;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        s = extras.getString("title");
        alert = extras.getString("alert");
        id = extras.getInt("id");
        createNotification(context, s, alert);
    }

    private void createNotification(Context context, String s, String alert) {
        PendingIntent pendIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                //.setSmallIcon(R.drawable.ic_action_add_term)
                .setContentTitle(s)
                .setContentText(alert);

        builder.setContentIntent(pendIntent);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id, builder.build());
    }
}