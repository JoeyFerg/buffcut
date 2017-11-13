package com.team_monkey.team_monkeysetup;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Buffer buffer;

    SharedPreferences preferences;
    Gson gson;
    String PREF_NAME = "BuffClip";
    String BUFFER = "Buffer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(PREF_NAME, 0);
        gson = new Gson();
        loadBuffer();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onStop(){
        super.onStop();
        saveBuffer();
    }

    protected void onDestroy(){
        super.onDestroy();
        buffer.RemoveClipboardEventListener();
        saveBuffer();
    }

    private void loadBuffer()
    {
        String bufferString = preferences.getString(BUFFER, "[]");
        android.util.Log.d("bufferstring", bufferString);
        if (bufferString != "[]") {
            LinkedList<String> bufferList = new LinkedList<>((ArrayList<String>)gson.fromJson(bufferString, new TypeToken<List<String>>(){}.getType()));
            buffer = new Buffer(this, bufferList);
        }
        else {
            buffer = new Buffer(this);
        }
    }

    private void saveBuffer()
    {
        String BufferString;
        BufferString = gson.toJson(buffer.Data());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(BUFFER, BufferString);
        editor.commit();
    }
}
