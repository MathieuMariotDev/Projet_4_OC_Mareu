package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mareu.R;
import com.example.mareu.Views.ListMeetingAdapter;
import com.example.mareu.modele.Meeting;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.AddMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity /*implements DatePickerDialog.OnDateSetListener */ {

    private FragmentListMeeting mFragmentListMeeting;
    private FragmentAddMeeting mFragmentAddMeeting;
    public Calendar filtrer;
    public ListMeetingAdapter mAdapter;
    public List<Meeting> mMeetingList;
    private MeetingApiService mApiService = new DummyMeetingApiService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureAndShowMainFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configureAndShowDetailFragment();
        //FloatingActionButton fab = findViewById(R.id.BtnAddMeeting);
        //configureRecylerView();

    }

    /* private void configureRecylerView() {
             this.mMeetingList= new ArrayList<>();
             this.mAdapter = new ListMeetingAdapter(this.mMeetingList);
             this.mRecyclerView.setAdapter(mAdapter);
             this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.filtre_date:
                Toast.makeText(this, "Filtre par date", Toast.LENGTH_LONG).show();
                //DialogFragment datePickerFragment2 = new DatePickerFragment();
                //datePickerFragment2.show(getSupportFragmentManager(), "datePickerFiltrer");
                //FilterMeetingFragment.newInstance();
                return true;
            case R.id.filtre_location:
                Toast.makeText(this, "Filtre par lieu", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }
    }

   /* @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        filtrer.set(year, month, dayOfMonth);
        Log.d("Debug", "onDateSet: " + filtrer);
        initListFilter(filtrer);
    }*/

   /* private void initListFilter(Calendar Filtrer) {
        mMeetingList = mApiService.getMeetingList();
        List<Meeting> MeetingByDay = new ArrayList<>();
        for (Meeting meeting : mMeetingList) {
            if (meeting.getDayTimeCalendar().get(Calendar.YEAR) == Filtrer.get(Calendar.YEAR) && meeting.getDayTimeCalendar().get(Calendar.MONTH) == Filtrer.get(Calendar.MONTH) && meeting.getDayTimeCalendar().get(Calendar.DAY_OF_MONTH) == Filtrer.get(Calendar.DAY_OF_MONTH))
                MeetingByDay.add(meeting);
        }
        Toast.makeText(this, "Les réunions a date indiqué", Toast.LENGTH_LONG).show();
        mAdapter = new ListMeetingAdapter(MeetingByDay);
        mFragmentListMeeting.mRecyclerView.setAdapter(mAdapter);
    }*/

    private void configureAndShowMainFragment() {
        mFragmentListMeeting = (FragmentListMeeting) getSupportFragmentManager().findFragmentById(R.id.frame_layout_list_meeting);

        if (mFragmentListMeeting == null) {
            mFragmentListMeeting = new FragmentListMeeting();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_list_meeting, mFragmentListMeeting)
                    .commit();
        }
    }

    public static void navigate(Context activity) {
        Intent intent = new Intent(activity, MainActivity.class); //Intent to open  AddNeighbourActivity
        ActivityCompat.startActivity(activity, intent, null); // Start of new  AddNeighbourActivity
    }


    private void configureAndShowDetailFragment() {
        mFragmentAddMeeting = (FragmentAddMeeting) getSupportFragmentManager().findFragmentById(R.id.frame_layout_add_meeting);

        if (mFragmentAddMeeting == null && findViewById(R.id.frame_layout_add_meeting) != null) {
            mFragmentAddMeeting = new FragmentAddMeeting();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_add_meeting, mFragmentAddMeeting)
                    .commit();
        }


    }

    @Optional
    @OnClick(R.id.BtnAddMeeting)
    void addMeeting() {
        AddMeeting.navigate(this);
    }


}