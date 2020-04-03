package com.example.mareu.modele;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.List;

public class Meeting {
    private int colormeeting;
    private String time;//the timetable meeting
    private String location;//location meeting
    private String topic;//topic meeting
    //private List<String> mList = new ArrayList<>(); // List mail
    private List<String> ListEmail;
    private Calendar DayTimeEndMeeting;
    private Calendar DayTimeMeeting;

    public Meeting(int colormeeting, Calendar DayTimeMeeting, Calendar DayTimeEndMeeting, String location, String topic, List<String> ListEmail) {//
        this.colormeeting = colormeeting;
        this.DayTimeMeeting = DayTimeMeeting;
        this.DayTimeEndMeeting = DayTimeEndMeeting;
        this.location = location;
        this.topic = topic;
        this.ListEmail = ListEmail;
    }

    public int getColormeeting() {
        return colormeeting;
    }

    public void setColormeeting(int ColorMeeting) {
        this.colormeeting = ColorMeeting;

    }

    public String getLocation() {
        return location;
    }

    public List<String> getList() {
        return ListEmail;
    }

    public void setList(List<String> Listemail) {
        this.ListEmail = Listemail;
    }

    public String getTopic() {
        return topic;
    }

    public Calendar getDayTimeCalendar() {
        return DayTimeMeeting;
    }

    public void setDayTimeCalendar(Calendar DayTimeMeeting) {
        this.DayTimeMeeting = DayTimeMeeting;
    }

    public Calendar getDayTimeEndCalendar() {
        return DayTimeEndMeeting;
    }

    public void setDayTimeEndCalendar(Calendar DayTimeEndMeeting) {
        this.DayTimeEndMeeting = DayTimeEndMeeting;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}

