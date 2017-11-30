package com.team_monkey.team_monkeysetup;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClipboardScreenActivity extends AppCompatActivity {
    Buffer buffer;
    SharedPreferences preferences;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ListAdapter mClipboardListAdapter;

    private ViewPager mViewPager;
    private ListView mClipboardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clipboard_screen);

        if(!isBufferServiceRunning(BufferService.class)) {
            Intent intent = new Intent(this, BufferService.class);
            startService(intent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        setContentView(R.layout.fragment_clipboard_tab);

        mClipboardListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, buffer.Data());

        mClipboardList = (ListView) findViewById(R.id.clip_container);
        mClipboardList.setAdapter(mClipboardListAdapter);

//        ListView favoritesList = findView ById(R.id.favorites_list);

//        ArrayAdapter FavoritesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, buffer.DataFavorites().toArray());
//        favoritesList.setAdapter(FavoritesAdapter);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clipboard_screen, menu);
        return true;
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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ClipboardTab clipboardTab = new ClipboardTab();
                    return clipboardTab;
                case 1:
                    FavoritesTab favoritesTab = new FavoritesTab();
                    return favoritesTab;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CLIPBOARD";
                case 1:
                    return "FAVORITES";
            }
            return null;
        }

    }
    public void sendNotifications(View view) {
        Context context = getApplicationContext();

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "my_channel_01";
        String name = "name";
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_LOW;
        }
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(id, name, importance);
        }
        if (mChannel != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mChannel.enableLights(true);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mNotificationManager != null) {
                assert mChannel != null;
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }
        Intent intent = new Intent(context , OpenOverlay.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1, //random id I created (Should be pulled out)
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder mBuilder = new Notification.Builder(
                    ClipboardScreenActivity.this, id)
                    .setContentTitle("Title")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher);

            Notification notification;
            Notification notificationTest;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification = mBuilder.build();
            } else {
                notification = mBuilder.getNotification();
            }

            notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
            if (mNotificationManager != null) {
                mNotificationManager.notify(1, notification);
            }
        }

    }
}
