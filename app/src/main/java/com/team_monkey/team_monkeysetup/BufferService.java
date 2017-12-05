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
import android.view.View;

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
        sendNotifications();
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


    public void sendNotifications() {
        Context context = getApplicationContext();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "my_channel_01";
        String name = "name";
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_LOW;
        }
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);
        }
        if (mChannel != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel.enableLights(true);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager != null) {
                assert mChannel != null;
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }



        Intent intent = new Intent(context , OpenOverlay.class);
        intent.putExtra("bufferData", buffer.Data().toArray(new String[buffer.Data().size()]));
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, //random id I created (Should be pulled out)
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder mBuilder = new Notification.Builder(
                    BufferService.this, id)
                    .setContentTitle("Title")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher);

            Notification notification;
            Notification notificationTest;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = mBuilder.build();
            } else {
                notification = mBuilder.getNotification();
            }

            notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
            if (mNotificationManager != null) {
                mNotificationManager.notify(1, notification);
            }
        }

    }





}
