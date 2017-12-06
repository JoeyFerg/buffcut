package com.team_monkey.team_monkeysetup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by jbern on 11/26/2017.
 */

public class dialogue extends DialogFragment {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialogue construction
        Context context = getContext();

        Activity activity = getActivity();
        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();
        String[] bufferDataArray = (String[])extras.get("bufferData");
        LinkedList bufferDataList = new LinkedList<String>(Arrays.asList(bufferDataArray));
        final Buffer buffer = new Buffer(context, bufferDataList);
        final String[] bufferDisplayData = buffer.Data().toArray(new String[buffer.Data().size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Pick a clipboard Item")
                .setItems(bufferDisplayData, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendSelectedItemToClipBoard(which, buffer);
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void sendSelectedItemToClipBoard(int index, Buffer buffer){
        buffer.FillClipboardWithElementAtIndex(index);
    }
}