package com.team_monkey.team_monkeysetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




public class MainActivity extends AppCompatActivity {
    Buffer buffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buffer = new Buffer(this);
        buffer.ApplyTestClip("Hello world!");
        buffer.SetMaxSize(25);
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onStop(){
        super.onStop();
    }

    protected void onDestroy(){
        super.onDestroy();
        buffer.RemoveClipboardEventListener();
    }
}
