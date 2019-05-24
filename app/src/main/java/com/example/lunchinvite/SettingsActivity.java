package com.example.lunchinvite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //int savedValue = getNotificationTime(this);

        createRadioButtons();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("successfully executed", data.getClass().getName());
    }

    public void createRadioButtons()
    {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_notification_time);
        final int[] notificationTimes = getResources().getIntArray(R.array.notification_times);

        for(int i = 0; i < notificationTimes.length; i++)
        {
            final Context context = this;
            RadioButton radioButton = new RadioButton(this);
            final int nTime = notificationTimes[i];
            radioButton.setText(String.valueOf(nTime) + " minutes before start");
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(context, "Option selected: " + String.valueOf(nTime) + " minutes", Toast.LENGTH_SHORT).show();
                    saveSelectedOption(nTime);
                }
            });
            radioGroup.addView(radioButton);

            if (notificationTimes[i] == getNotificationTime(this))
            {
                radioButton.setChecked(true);
            }
        }
    }

    public void saveSelectedOption(int minutes)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Notification Time before event start", minutes);
        editor.apply();
    }

    static public int getNotificationTime(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", MODE_PRIVATE);
        return sharedPreferences.getInt("Notification Time before event start", 5);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_activity, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_newInvitations)
        {
            startActivityForResult(new Intent(this, AddInvitationActivity.class), 1);
            return true;
        }
        else if (id == R.id.nav_invitations)
        {
            startActivityForResult(new Intent(this, ShowInvitations.class), 1);
            //startActivity(new Intent(this, ShowInvitations.class));
            return true;
        }
        else if (id == R.id.nav_settings)
        {
            //startActivityForResult(new Intent(this, SettingsActivity.class), 1);
            return true;
        }
        else if (id == R.id.nav_profile)
        {
            startActivityForResult(new Intent(this, MainActivity.class), 1);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
