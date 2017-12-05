package com.team_monkey.team_monkeysetup;

/**
 * Created by jbern on 11/13/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.LinkedList;


public class OpenOverlay extends Activity {

     protected void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);





         dialogue dialogueTest = new dialogue();
         dialogueTest.show(getFragmentManager(), "tag1");
    }
}
