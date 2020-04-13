package com.example.mareu.service;

import com.example.mareu.modele.Meeting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private static List<Meeting> mMeetingList = new ArrayList<>();


    @Override
    public void createMeeting(Meeting meeeting) {
        mMeetingList.add(meeeting);
    } // normal

    public List<Meeting> getMeetingList() {
        return mMeetingList;
    }

    @Override
    public void DeleteMeeting(Meeting meeting) {
        mMeetingList.remove(meeting);
    }

    @Override
    public List<Meeting> FilterByDate(Calendar FilterDate) {
        List<Meeting> MeetingByDay = new ArrayList<>();
        for (Meeting meeting : mMeetingList) {
            if (meeting.getDayTimeCalendar().get(Calendar.YEAR) == FilterDate.get(Calendar.YEAR) && meeting.getDayTimeCalendar().get(Calendar.MONTH) == FilterDate.get(Calendar.MONTH) && meeting.getDayTimeCalendar().get(Calendar.DAY_OF_MONTH) == FilterDate.get(Calendar.DAY_OF_MONTH))
                MeetingByDay.add(meeting);
        }
        return MeetingByDay;
    }

    @Override
    public List<Meeting> FilterByLocation(String Location) {
        List<Meeting> MeetingByLocation = new ArrayList<>();
        for (Meeting meeting : mMeetingList) {
            if (meeting.getLocation().equals(Location)) {
                MeetingByLocation.add(meeting);
            }
        }

        return MeetingByLocation;
    }

    @Override
    public boolean LockedTime(Calendar CalendarMeeting, Calendar CalendarEndMeeting,String Location) {
    boolean autorizedCreation=true;
        for (Meeting meeting : mMeetingList) {

            if (((CalendarEndMeeting.get(Calendar.YEAR) == meeting.getDayTimeEndCalendar().get(Calendar.YEAR) && CalendarEndMeeting.get(Calendar.MONTH) == meeting.getDayTimeEndCalendar().get(Calendar.MONTH) && CalendarEndMeeting.get(Calendar.DAY_OF_MONTH) == meeting.getDayTimeEndCalendar().get(Calendar.DAY_OF_MONTH))) && meeting.getLocation().equals(Location)) {


                if (!((CalendarMeeting.before(meeting.getDayTimeCalendar()) || CalendarMeeting.after(meeting.getDayTimeEndCalendar())) && (CalendarEndMeeting.before(meeting.getDayTimeCalendar()) || CalendarEndMeeting.after(meeting.getDayTimeEndCalendar())))) {
                    autorizedCreation=false;
                }


            }
            else {
                autorizedCreation=true;
            }
        }
        return autorizedCreation;
    }

    @Override
    public void onDestroy() {
        mMeetingList.clear();
    }

}
