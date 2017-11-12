package com.team_monkey.team_monkeysetup;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendNotification(View view) {

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "my_channel_01";
        String name = "name";
        int importance = NotificationManager.IMPORTANCE_LOW;
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
            mNotificationManager.createNotificationChannel(mChannel);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(MainActivity.this)
                    .setContentTitle("New Message")
                    .setContentText("You've received new messages.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId(id)
                    .build();
        }


         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             Notification.Builder mBuilder = new Notification.Builder(MainActivity.this, id).setContentTitle("Title");
             mNotificationManager.notify(001, mBuilder.build());
        }




    }
}
