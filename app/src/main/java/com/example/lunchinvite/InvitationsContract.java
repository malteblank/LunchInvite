package com.example.lunchinvite;

import android.provider.BaseColumns;

public class InvitationsContract
{
    private InvitationsContract()
    {

    }

    public static final class RecyclerViewEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "invitation_list";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_PARTICIPANTS = "participants";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
