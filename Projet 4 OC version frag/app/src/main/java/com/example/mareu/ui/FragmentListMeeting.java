package com.example.mareu.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.Views.ListMeetingAdapter;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.modele.Meeting;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListMeeting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListMeeting extends Fragment {

    //RecyclerView mRecyclerView;
    @BindView(R.id.recycler_view_main)
    public RecyclerView mRecyclerView;
    public ListMeetingAdapter mAdapter;
    public List<Meeting> mMeetingList;
    private MeetingApiService mApiService= new DummyMeetingApiService();

    public FragmentListMeeting() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentListMeeting newInstance() {
        FragmentListMeeting fragment = new FragmentListMeeting();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
        configureRecylerView();
        return view;
    }

    private void configureRecylerView() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMeetingList=mApiService.getMeetingList();
        this.mAdapter = new ListMeetingAdapter(this.mMeetingList);
        this.mRecyclerView.setAdapter(mAdapter);
    }
    private void initList(){
        mMeetingList=mApiService.getMeetingList();
        this.mAdapter = new ListMeetingAdapter(this.mMeetingList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event){
        mApiService.DeleteMeeting(event.meeting);
        initList();
    }
}
