package com.team_monkey.team_monkeysetup;

/**
 * Created by jbern on 11/13/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;


public class OpenOverlay extends Activity {

     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         dialogue dialogueTest = new dialogue();
         Log.w("debug", "dialogTest");
         dialogueTest.show(getFragmentManager(), "tag1");
    }
}
