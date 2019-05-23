package com.example.lunchinvite;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class InvitationsAdapter extends RecyclerView.Adapter<InvitationsAdapter.InvitationsViewHolder>
{
    public Context mContext;
    public Cursor mCursor;

    public InvitationsAdapter(Context context, Cursor cursor)
    {
        mContext = context;
        mCursor = cursor;
    }

    public class InvitationsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView dateText;
        public TextView timeText;
        public TextView participantsText;

        public InvitationsViewHolder(@NonNull View itemView)
        {
            super(itemView);
            dateText = itemView.findViewById(R.id.textView_date);
            timeText = itemView.findViewById(R.id.textView_time);
            participantsText = itemView.findViewById(R.id.textView_participants);
        }
    }

    @NonNull
    @Override
    public InvitationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.invitation_item, viewGroup, false);
        return new InvitationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitationsViewHolder invitationsViewHolder, int position)
    {
        if (!mCursor.moveToPosition(position))
        {
            return;
        }

        String date = mCursor.getString(mCursor.getColumnIndex(InvitationsContract.RecyclerViewEntry.COLUMN_DATE));
        String time = mCursor.getString(mCursor.getColumnIndex(InvitationsContract.RecyclerViewEntry.COLUMN_TIME));
        String participants = mCursor.getString(mCursor.getColumnIndex(InvitationsContract.RecyclerViewEntry.COLUMN_PARTICIPANTS));
        long id = mCursor.getLong(mCursor.getColumnIndex(InvitationsContract.RecyclerViewEntry._ID));

        invitationsViewHolder.dateText.setText(date);
        invitationsViewHolder.timeText.setText(time);
        invitationsViewHolder.participantsText.setText(participants);
        invitationsViewHolder.itemView.setTag(id);

        invitationsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext.getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                addAlert(mCursor.getString(mCursor.getColumnIndex(InvitationsContract.RecyclerViewEntry.COLUMN_DATE)), mCursor.getString(mCursor.getColumnIndex(InvitationsContract.RecyclerViewEntry.COLUMN_TIME)));
            }
        });
    }

    @TargetApi(19)
    public void addAlert(String date, String time)
    {

        String[] dateArray = date.split("-");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);

        String[] timeArray = time.split(":");
        int hour = Integer.parseInt(timeArray[0]);
        int minute = Integer.parseInt(timeArray[1]);
        int second = 0;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        int temp = -1 * (SettingsActivity2.getNotificationTime(mContext));
        c.add(Calendar.MINUTE, temp);
        c.set(Calendar.SECOND, second);
        c.setTimeInMillis(System.currentTimeMillis());

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = (new Intent(mContext.getApplicationContext(), AlertReceiver.class));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext.getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(c.before(Calendar.getInstance()))
        {
            return;
        }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
            Toast.makeText(mContext.getApplicationContext(), "Alert added", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount()
    {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor)
    {
        if(mCursor != null)
        {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null)
        {
            notifyDataSetChanged();
        }
    }
}
