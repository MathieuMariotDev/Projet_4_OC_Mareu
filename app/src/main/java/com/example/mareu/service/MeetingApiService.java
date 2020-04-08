package com.example.mareu.service;

import com.example.mareu.modele.Meeting;

import java.util.Calendar;
import java.util.List;

public interface MeetingApiService {

    void createMeeting(Meeting meeting);

    List<Meeting> getMeetingList();

    void DeleteMeeting(Meeting meeting);

    List<Meeting> FilterByDate(Calendar FilterDate);

    List<Meeting> FilterByLocation(String Location);

    boolean LockedTime(Calendar CalendarMeeting, Calendar CalendarEndMeeting,String Location);

    void onDestroy();
}
