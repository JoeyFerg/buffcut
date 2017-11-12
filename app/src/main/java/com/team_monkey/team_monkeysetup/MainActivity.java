package com.team_monkey.team_monkeysetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Buffer buffer = new Buffer(this);
        buffer.ApplyTestClip("Hello world!");
        buffer.SetMaxSize(25);
    }
}
