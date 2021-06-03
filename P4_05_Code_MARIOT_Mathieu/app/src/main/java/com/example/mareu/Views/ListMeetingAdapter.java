package com.example.mareu.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.modele.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ListMeetingAdapter extends RecyclerView.Adapter<MeetingViewHolder> {
    private List<Meeting> mMeetingList;

    public ListMeetingAdapter(List<Meeting> mMeetingList) {
        this.mMeetingList = mMeetingList;
    }


    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_meeting_item, parent, false);

        return new MeetingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MeetingViewHolder viewHolder, int position) {
        Meeting meeting = mMeetingList.get(position);
        viewHolder.updateWithMeeting(meeting);
        viewHolder.DeleteItem.setOnClickListener(new View.OnClickListener() { // Observe le click sur le bouton Delete
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting)); // Event transmi a fragmentListMeeting
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mMeetingList.size();
    }
}
