package com.example.lunchinvite;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
