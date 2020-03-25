package com.example.mareu.Views;

import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.modele.Meeting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_list)
    TextView Information;
    @BindView(R.id.item_list_mail)
    TextView ListMail;
    //@BindView(R.id.icone_color)
    @BindView(R.id.item_list_delete)
    ImageButton DeleteItem;
    String SourceInformation;

    public MeetingViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void updateWithMeeting(Meeting meeting){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
        SourceInformation= "<b>" + meeting.getLocation() +" - " + dateFormat.format(meeting.getDayTimeCalendar().getTime()) +" - "+ meeting.getTopic() + "</b>";
        this.Information.setText(Html.fromHtml(SourceInformation));
        this.ListMail.setText(meeting.getList().toString());
    }
}
