package com.team_monkey.team_monkeysetup;

import android.app.ListActivity;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class ClipboardAdapter extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.clipboard_tab);
    }
}