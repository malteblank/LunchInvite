package com.example.lunchinvite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddInvitationActivity extends AppCompatActivity {

    public int counter = 0;
    public SQLiteDatabase mDatabase;

    public InvitationsAdapter mAdapter;

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
        setContentView(R.layout.activity_add_invitation);

        InvitationsDBHelper dbHelper = new InvitationsDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        mAdapter = new InvitationsAdapter(this, getAllItems());

        Button add = findViewById(R.id.btn_addInvitation);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addItem();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditTextday = findViewById(R.id.edit_day);
        mEditTextmonth = findViewById(R.id.edit_month);
        mEditTextyear = findViewById(R.id.edit_year);

        mEditTexthour = findViewById(R.id.edit_hour);
        mEditTextminute = findViewById(R.id.edit_minutes);

        mEditTextparticipants = findViewById(R.id.edit_participants);

    }

    public void addItem() {

        mEditTextday = findViewById(R.id.edit_day);
        mEditTextmonth = findViewById(R.id.edit_month);
        mEditTextyear = findViewById(R.id.edit_year);

        mEditTexthour = findViewById(R.id.edit_hour);
        mEditTextminute = findViewById(R.id.edit_minutes);

        mEditTextparticipants = findViewById(R.id.edit_participants);

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

}
