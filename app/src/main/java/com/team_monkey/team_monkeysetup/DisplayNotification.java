package com.team_monkey.team_monkeysetup;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class DisplayNotification implements Runnable {
    Context mContext;
    NotificationManager mNotificationManager;

    private void DisplayCustomNotification(Context mContext) {
        this.mContext = mContext;
        mNotificationManager = (NotificationManager)
                mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @Override
    public void run() {
        makeNotification(mContext);
    }

    private void makeNotification(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                123, //random id I created (Should be pulled out)
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("CTRL-V Notification")
                .setContentText("test text")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher);

        NotificationManager notificationManager;
        Notification n;
        notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            n = builder.build();
        } else{
            n = builder.getNotification(); //deprecated
        }
        notificationManager.notify(123, n);
    }
}
