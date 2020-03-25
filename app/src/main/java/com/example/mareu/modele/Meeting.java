package com.example.mareu.modele;

import java.util.Calendar;
import java.util.List;

public class Meeting {
    private String time;//the timetable meeting
    private String location;//location meeting
    private String topic;//topic meeting
    //private List<String> mList = new ArrayList<>(); // List mail
    private List<String> ListEmail;
    private Calendar DayTimeMeeting;

    public Meeting (Calendar DayTimeMeeting, String location, String topic, List<String> ListEmail){//
        this.DayTimeMeeting=DayTimeMeeting;
        this.location=location;
        this.topic=topic;
        this.ListEmail=ListEmail;
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


    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /*public String getList() {
        return mList;
    }
    public void setList (String list) {
        mList = list;
    }*/
}

