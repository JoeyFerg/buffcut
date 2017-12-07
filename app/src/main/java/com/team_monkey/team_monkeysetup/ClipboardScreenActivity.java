package com.team_monkey.team_monkeysetup;

import android.app.ActivityManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.os.IBinder;

public class ClipboardScreenActivity extends AppCompatActivity {

    SharedPreferences preferences;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    private Buffer buffer;
    private BufferService bufferService;
    private Intent serviceIntent;
    private boolean bufferBound = false;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clipboard_screen);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);



        serviceIntent = new Intent(this, BufferService.class);
        if(!isBufferServiceRunning(BufferService.class)) {
            startService(serviceIntent);
        }

    }

    public void refreshNow() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void setViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ClipboardTab(), "Clipboard");
        adapter.addFragment(new FavoritesTab(), "Favorites");
    }

    protected void onResume() {
        super.onResume();
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void onPause() {
        super.onPause();
        unbindService(serviceConnection);
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isBufferServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void AddTabFragments()
    {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putStringArray("bufferTab", buffer.DataArray());
        ClipboardTab clipboardTab = new ClipboardTab();
        clipboardTab.setArguments(bundle);

        Bundle bundleFav = new Bundle();
        bundleFav.putStringArray("bufferTab", buffer.DataArray());
        bundleFav.putStringArray("bufferFavTab", buffer.DataFavArray());
        FavoritesTab favoritesTab = new FavoritesTab();
        favoritesTab.setArguments(bundleFav);

        viewPagerAdapter.addFragments(clipboardTab, "Clipboard");
        viewPagerAdapter.addFragments(favoritesTab, "Favorites");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BufferService.BufferBinder binder = (BufferService.BufferBinder) service;
            bufferService = binder.getService();
            buffer = new Buffer(ClipboardScreenActivity.this, bufferService.getBufferData());
            android.util.Log.d("ServiceConnection", "Buffer Log: ");
            buffer.LogBuffer();
            bufferBound = true;
            AddTabFragments();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bufferBound = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_clipboard_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent for settings page
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            //Start Activity
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
