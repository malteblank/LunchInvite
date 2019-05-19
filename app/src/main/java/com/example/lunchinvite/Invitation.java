package com.example.lunchinvite;

public class Invitation
{
    private String date;
    private String time;
    private String participants;
    private String title;

    public Invitation()
    {

    }

    public Invitation (String date, String time, String participants, String title)
    {
        this.date = date;
        this.time = time;
        this.participants = participants;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
