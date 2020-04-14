package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.example.mareu.R;
import com.example.mareu.modele.Meeting;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeeting extends AppCompatActivity {

    private FragmentAddMeeting mFragmentAddMeeting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        configureAndShowAddFragment();
        ButterKnife.bind(this);
    }

    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeeting.class); //Intent to open  AddNeighbourActivity
        ActivityCompat.startActivity(activity, intent, null); // Start of new  AddNeighbourActivity
    }


    private void configureAndShowAddFragment() {
        mFragmentAddMeeting = (FragmentAddMeeting) getSupportFragmentManager().findFragmentById(R.id.frame_layout_add_meeting);

        if (mFragmentAddMeeting == null) {
            mFragmentAddMeeting = new FragmentAddMeeting();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_add_meeting, mFragmentAddMeeting)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentAddMeeting.mApiService.onDestroy();
    }
}
