package com.npelly.sixfourdeploy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DeployService extends Service {
    private static final int ONGOING_NOTIFICATION_ID = 1;


    @Override
    public void onCreate() {
        Base.logv("DeployService onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Base.logv("DeployService onStartCommand()");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getText(R.string.ongoing_notification_title))
                .setContentText(getText(R.string.ongoing_notification_message) + " " + Base.get().getNetwork().getStatus())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setTicker(getText(R.string.ongoing_ticker_text))
                .build();

        startForeground(ONGOING_NOTIFICATION_ID, notification);

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Base.logv("DeployService onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
