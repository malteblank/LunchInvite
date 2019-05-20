package com.example.lunchinvite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InvitationsDBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "invitations.db";
    public static final int DATABASE_VERSION = 2;


    public InvitationsDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String SQL_CREATE_INVITATIONLIST_TABLE = "CREATE TABLE " +
                InvitationsContract.RecyclerViewEntry.TABLE_NAME + " ( " +
                InvitationsContract.RecyclerViewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                InvitationsContract.RecyclerViewEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                InvitationsContract.RecyclerViewEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                InvitationsContract.RecyclerViewEntry.COLUMN_PARTICIPANTS + " TEXT NOT NULL, " +
                InvitationsContract.RecyclerViewEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_INVITATIONLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + InvitationsContract.RecyclerViewEntry.TABLE_NAME);
        onCreate(db);
    }
}
