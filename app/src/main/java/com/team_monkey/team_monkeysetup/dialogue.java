package com.team_monkey.team_monkeysetup;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by jbern on 11/26/2017.
 */

public class dialogue extends DialogFragment {
    private Buffer buffer;
    private BufferService bufferService;
    private Intent serviceIntent;
    private boolean bufferBound = false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialogue construction
        Context context = getContext();
        Log.w("debug", "service binding");
        context.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.w("debug", "service bound");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final CharSequence[] colors = new String[2];
        colors[0] = "red";
        colors[1] = "green";
        builder.setTitle("Pick a clipboard Item")
                .setItems(colors, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.w("tag", ""+ colors[which]);
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BufferService.BufferBinder binder = (BufferService.BufferBinder) service;
            bufferService = binder.getService();
            buffer = new Buffer( getContext(), bufferService.getBufferData());
            android.util.Log.d("ServiceConnection", "Buffer Log: ");
            buffer.LogBuffer();
            Log.w("test", "" + 100);
            bufferBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bufferBound = false;
        }
    };
}