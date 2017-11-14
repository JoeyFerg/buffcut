package com.team_monkey.team_monkeysetup;

/**
 * Created by jbern on 11/13/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class OpenOverlay extends Activity {

     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         Intent intent = getIntent();
         Bundle extras = intent.getExtras();
         assert extras != null;
         Integer tmp = extras.getInt("testVal");
         if (tmp == 0){
             tmp = extras.getInt("testVal2");
         }
         Log.w("tag", ""+tmp);
         setContentView(R.layout.testfile);
    }
}
