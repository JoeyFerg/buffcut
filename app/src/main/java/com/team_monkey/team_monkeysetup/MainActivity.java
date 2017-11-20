package com.team_monkey.team_monkeysetup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Buffer buffer;

    SharedPreferences preferences;
    Gson gson;
    String PREF_NAME = "BuffClip";
    String BUFFER = "Buffer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(PREF_NAME, 0);
        gson = new Gson();
        loadBuffer();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onStop(){
        super.onStop();
        saveBuffer();
    }

    protected void onDestroy(){
        super.onDestroy();
        buffer.RemoveClipboardEventListener();
        saveBuffer();
    }

    private void loadBuffer()
    {
        String bufferString = preferences.getString(BUFFER, "[]");
        android.util.Log.d("bufferstring", bufferString);
        if (bufferString != "[]") {
            LinkedList<String> bufferList = new LinkedList<>((ArrayList<String>)gson.fromJson(bufferString, new TypeToken<List<String>>(){}.getType()));
            buffer = new Buffer(this, bufferList);
        }
        else {
            buffer = new Buffer(this);
        }
    }

    private void saveBuffer()
    {
        String BufferString;
        BufferString = gson.toJson(buffer.Data());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(BUFFER, BufferString);
        editor.commit();
    }

    public void sendNotifications(View view) {
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
        intent.putExtra("testVal", 17);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, //random id I created (Should be pulled out)
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Intent intentTest = new Intent(context , OpenOverlay.class); //Delete from here 0
        intentTest.putExtra("testVal2", 23);
        PendingIntent pendingIntentTest = PendingIntent.getActivity(context,
                2, //random id I created (Should be pulled out)
                intentTest,
                PendingIntent.FLAG_UPDATE_CURRENT);// To Here 0

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             Notification.Builder mBuilder = new Notification.Builder(
                     MainActivity.this, id)
                     .setContentTitle("Title")
                     .setContentIntent(pendingIntent)
                     .setSmallIcon(R.mipmap.ic_launcher);
             Notification.Builder mBuilderTest = new Notification.Builder( //Delete from here 2
                     MainActivity.this, id)
                     .setContentTitle("Title")
                     .setContentIntent(pendingIntentTest)
                     .setSmallIcon(R.mipmap.ic_launcher); //to here 2

             Notification notification;
             Notification notificationTest;

             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                 notification = mBuilder.build();
                 notificationTest = mBuilderTest.build();
             } else {
                 notification = mBuilder.getNotification();
                 notificationTest = mBuilderTest.getNotification();
             }

             notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
             if (mNotificationManager != null) {
                 mNotificationManager.notify(1, notification);
                 mNotificationManager.notify(2, notificationTest);
             }
         }

    }
}
