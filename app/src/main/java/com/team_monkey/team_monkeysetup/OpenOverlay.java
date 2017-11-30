package com.team_monkey.team_monkeysetup;

/**
 * Created by jbern on 11/13/2017.
 */

import android.app.Activity;
import android.os.Bundle;


public class OpenOverlay extends Activity {

     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         Dialogue dialogueTest = new Dialogue();
         dialogueTest.show(getFragmentManager(), "tag1");
    }
}
