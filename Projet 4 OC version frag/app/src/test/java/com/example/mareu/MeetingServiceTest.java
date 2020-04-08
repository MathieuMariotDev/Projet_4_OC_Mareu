package com.example.mareu;

import android.text.format.DateFormat;

import com.example.mareu.modele.Meeting;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static java.util.Calendar.AM;


@RunWith(JUnit4.class)
public class MeetingServiceTest {
    private MeetingApiService mApiService = new DummyMeetingApiService();
    private Meeting meetingTwo;
    private Meeting meetingOne;


    @Before
    public void setup() {

        Calendar mCalendarStart = Calendar.getInstance();
        Calendar mTimeEnd = Calendar.getInstance();
        Calendar CalendarStart = Calendar.getInstance();
        Calendar TimeEnd = Calendar.getInstance();
        mCalendarStart.set(2020, 4, 10, 9, 30);

        mTimeEnd.set(2020, 4, 10, 10, 30);

        List<String> Listemail = Arrays.asList("Math.mariot@gmail.com", "Lanfe666@gmail.com");
        meetingOne = new Meeting(1, mCalendarStart, mTimeEnd, "Réunion 1", "Peach", Listemail);//Réunion 1 // 5 à 8h30
        mApiService.createMeeting(meetingOne);

        CalendarStart.set(2020, 4, 15, 8, 30);

        TimeEnd.set(2020, 4, 15, 9, 29);

        meetingTwo = new Meeting(1, CalendarStart, TimeEnd, "Réunion 3", "Mario", Listemail);// Réunion 3 // 4 à 8h30
        mApiService.createMeeting(meetingTwo);
    }


    @Test
    public void getMeetingWithSucces() {
        List<Meeting> mListmeetingExpected = mApiService.getMeetingList();
        assertTrue(mListmeetingExpected.contains(meetingTwo));
        assertTrue(mListmeetingExpected.contains(meetingOne));
    }

    @Test
    public void FilterByDate() {
        List<Meeting> MeetingByDate;
        Calendar calendarFilter = Calendar.getInstance();
        calendarFilter.set(2020, 4, 10);
        MeetingByDate = mApiService.FilterByDate(calendarFilter);
        assertFalse(MeetingByDate.contains(meetingTwo));
        assertTrue(MeetingByDate.contains(meetingOne));
    }

    @Test
    public void FilterByLocation() {
        List<Meeting> MeetingByLocation;
        MeetingByLocation = mApiService.FilterByLocation("Réunion 3");
        assertFalse(MeetingByLocation.contains(meetingOne));
        assertTrue(MeetingByLocation.contains(meetingTwo));

    }

    @Test
    public void DestoyListOnRotation() {
        mApiService.onDestroy();
        assertTrue(mApiService.getMeetingList().isEmpty());
    }

    @Test
    public void DeleteMeeting() {
        List<Meeting> mListmeetingExpected = mApiService.getMeetingList();
        mApiService.DeleteMeeting(meetingTwo);
        assertFalse(mListmeetingExpected.contains(meetingTwo));
        assertTrue(mListmeetingExpected.contains(meetingOne));
    }

    @Test
    public void LockedTime() {
        boolean AutorizationCreat;
        String Location = "Réunion 3";
        Calendar CalendarTimeDateStart = Calendar.getInstance();
        Calendar CalendarTimeDateEnd = Calendar.getInstance();
        CalendarTimeDateStart.set(2020, 4, 15, 8, 45);
        CalendarTimeDateEnd.set(2020, 4, 15, 9, 10);

        AutorizationCreat = mApiService.LockedTime(CalendarTimeDateStart, CalendarTimeDateEnd, Location);
        assertFalse(AutorizationCreat);
    }
}