package com.team_monkey.team_monkeysetup;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.app.PendingIntent;
import android.content.*;
import android.app.*;
import com.google.gson.Gson;

import java.util.LinkedList;

public class BufferService extends Service {
    Buffer buffer;
    private final IBinder bufferBinder = new BufferBinder();

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
        runInForeground();
    }

    private void runInForeground()
    {
        Intent notificationIntent = new Intent(this, ClipboardScreenActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        initChannels(this);

        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("My Awesome App")
                .setContentText("Doing some work...")
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification);
    }

    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
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
}
