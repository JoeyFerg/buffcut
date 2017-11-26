package com.team_monkey.team_monkeysetup;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

public class BufferService extends Service {
    Buffer buffer;


    public BufferService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        buffer = new Buffer(this);
        buffer.loadBuffer();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        buffer.RemoveClipboardEventListener();
        Log.i("LocalService", "Received stop command");
    }
}
