package com.example.lunchinvite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShowInvitations extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public int counter = 0;

    public SQLiteDatabase mDatabase;


    public RecyclerView recyclerView;
    public InvitationsAdapter mAdapter;
    public RecyclerView.LayoutManager layoutManager;

    public EditText mEditTextday;
    public EditText mEditTextmonth;
    public EditText mEditTextyear;

    public EditText mEditTexthour;
    public EditText mEditTextminute;

    public EditText mEditTextparticipants;

    public String mInvitationDate = "";
    public String mInvitationTime = "";
    public String mInvitationParticipants = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        {
            counter = savedInstanceState.getInt("counter");
        }
        setContentView(R.layout.activity_show_invitations);

        InvitationsDBHelper dbHelper = new InvitationsDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.recyclerView_invitations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new InvitationsAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        mEditTextday = findViewById(R.id.edit_day);
        mEditTextmonth = findViewById(R.id.edit_month);
        mEditTextyear = findViewById(R.id.edit_year);

        mEditTexthour = findViewById(R.id.edit_hour);
        mEditTextminute = findViewById(R.id.edit_minutes);

        mEditTextparticipants = findViewById(R.id.edit_participants);

        Button add = findViewById(R.id.btn_addInvitation);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addItem();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        outState.putInt("counter", counter);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        counter = savedInstanceState.getInt("counter");
    }

    protected void onStart(Bundle savedInstanceState) {
        super.onStart();
        Log.d("started",getClass().getName());
    }

    protected void onPause(Bundle savedInstanceState) {
        super.onPause();
        Log.d("paused", getClass().getName());
    }

    protected void onDestroy(Bundle savedInstanceState) {
        super.onDestroy();
        Log.d("destroyed", getClass().getName());
    }

    protected void onRestart(Bundle savedInstanceState) {
        super.onRestart();
        Log.d("restarted", getClass().getName());
    }

    protected void onStop(Bundle savedInstanceState) {
        super.onStop();
        Log.d("stop", getClass().getName());
    }

    public void addItem() {
        if (mEditTextday.getText().toString().trim().length() == 0 || mEditTextmonth.getText().toString().trim().length() == 0 || mEditTextyear.getText().toString().trim().length() == 0 || mEditTexthour.getText().toString().trim().length() == 0 || mEditTextminute.getText().toString().trim().length() == 0 || mEditTextparticipants.getText().toString().trim().length() == 0)
        {
            return;
        }

        ContentValues cv = new ContentValues();

        mInvitationDate = mEditTextyear.getText().toString().trim() + "-" + mEditTextmonth.getText().toString().trim() + "-" + mEditTextday.getText().toString().trim();
        cv.put(InvitationsContract.RecyclerViewEntry.COLUMN_DATE, mInvitationDate);
        mInvitationTime = mEditTexthour.getText().toString().trim() + ":" + mEditTextminute.getText().toString().trim();
        cv.put(InvitationsContract.RecyclerViewEntry.COLUMN_TIME, mInvitationTime);
        mInvitationParticipants = mEditTextparticipants.getText().toString();
        cv.put(InvitationsContract.RecyclerViewEntry.COLUMN_PARTICIPANTS, mInvitationParticipants);

        mDatabase.insert(InvitationsContract.RecyclerViewEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());

        mEditTextday.getText().clear();
        mEditTextmonth.getText().clear();
        mEditTextyear.getText().clear();

        mEditTexthour.getText().clear();
        mEditTextminute.getText().clear();

        mEditTextparticipants.getText().clear();

    }

    public Cursor getAllItems()
    {
        return mDatabase.query(
                InvitationsContract.RecyclerViewEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                InvitationsContract.RecyclerViewEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_invitations, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
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
            startActivity(new Intent(this, AddInvitationActivity.class));
            return true;
        }
        else if (id == R.id.nav_invitations)
        {
            startActivity(new Intent(this, ShowInvitations.class));
            return true;
        }
        else if (id == R.id.nav_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        else if (id == R.id.nav_profile)
        {
            startActivity(new Intent(this, Profile.class));
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
