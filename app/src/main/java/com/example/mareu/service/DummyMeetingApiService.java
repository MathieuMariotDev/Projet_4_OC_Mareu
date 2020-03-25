package com.example.mareu.service;

import com.example.mareu.modele.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {

    private static List<Meeting> mMeetingList=new ArrayList<>();
    @Override
    public void createMeeting(Meeting meeeting) {
        mMeetingList.add(meeeting);
    }
    public List<Meeting> getMeetingList(){
        return mMeetingList;
    }

    @Override
    public void DeleteMeeting(Meeting meeting) {
        mMeetingList.remove(meeting);
    }
}
