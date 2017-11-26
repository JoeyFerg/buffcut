package com.team_monkey.team_monkeysetup;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by jbern on 11/26/2017.
 */

public class dialogue extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the Builder class for convenient dialogue construction
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
}