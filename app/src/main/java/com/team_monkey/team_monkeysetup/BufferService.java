package com.team_monkey.team_monkeysetup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.app.PendingIntent;
import android.content.*;
import android.app.*;
import java.util.LinkedList;

public class BufferService extends Service {
    Buffer buffer;
    private final IBinder bufferBinder = new BufferBinder();

    private Buffer.BufferCallback buffCallBack = new Buffer.BufferCallback() {
        @Override
        public void callBack() {
            sendNotifications();
        }
    };

    public class BufferBinder extends Binder {
        BufferService getService() {
            return BufferService.this;
        }
    }

    public BufferService() {
    }

    @Override
    public void onCreate()
    {
        buffer = new Buffer(this);
        buffer.LoadBuffer();
        buffer.RegisterBufferCallback(buffCallBack);
        runInForeground();
        sendNotifications();
    }

    private void runInForeground()
    {
        Intent notificationIntent = new Intent(this, ClipboardScreenActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        initChannels(this, "default", "Service");

        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("My Awesome App")
                .setContentText("Doing some work...")
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification);
    }

    public void initChannels(Context context, String channelId, String channelName) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return bufferBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        buffer.RemoveClipboardEventListener();
        Log.i("LocalService", "Received stop command");
    }

    public LinkedList<String> getBufferData(){
        return buffer.Data();
    }


    public void sendNotifications() {
        Context context = getApplicationContext();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "my_channel_01";
        String name = "name";

        initChannels(this, id, name);

        Intent intent = new Intent(context , OpenOverlay.class);
        intent.putExtra("bufferData", buffer.DataArray());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, //random id I created (Should be pulled out)
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this, id);
        nb.setContentTitle("BuffCut").setContentIntent(pendingIntent).setSmallIcon(R.mipmap.ic_launcher);


        Notification notification = nb.build();

        notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        if (mNotificationManager != null) {
            mNotificationManager.notify(1, notification);
        }
    }

}
