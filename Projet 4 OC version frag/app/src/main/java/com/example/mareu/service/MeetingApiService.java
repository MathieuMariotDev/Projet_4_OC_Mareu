package com.example.mareu.service;

import com.example.mareu.modele.Meeting;

import java.util.List;

public interface MeetingApiService {

    void createMeeting(Meeting meeeting);

    List<Meeting> getMeetingList();

    void DeleteMeeting(Meeting meeting);


}
