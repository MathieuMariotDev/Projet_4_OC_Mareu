package com.example.mareu.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity /*implements DatePickerDialog.OnDateSetListener*/ {

    private FragmentListMeeting mFragmentListMeeting;
    private FragmentAddMeeting mFragmentAddMeeting;
    public Calendar filtrer = Calendar.getInstance();
    private MeetingApiService mApiService = new DummyMeetingApiService();
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureAndShowMainFragment();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        configureAndShowDetailFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.list_meeting).setVisible(false);
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
                DatePickerFragment datePickerFragment = new DatePickerFragment().newIntance();
                datePickerFragment.setCallBack(onDate);
                datePickerFragment.show(getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
                menu.findItem(R.id.list_meeting).setVisible(true);
                return true;
            case R.id.filtre_location:
                //Toast.makeText(this, "Filtre par lieu sélectionner ", Toast.LENGTH_LONG).show();
                AlerteDialogSpinner();
                menu.findItem(R.id.list_meeting).setVisible(true);
                return true;
            case R.id.list_meeting:
                Toast.makeText(this, "La liste complête des réunnions ", Toast.LENGTH_LONG).show();
                mFragmentListMeeting.onResume();
                menu.findItem(R.id.list_meeting).setVisible(false);
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void initListWithFilter(List<Meeting> ListMeetingWithFilter) {
        mFragmentListMeeting.mAdapter = new ListMeetingAdapter(ListMeetingWithFilter);
        mFragmentListMeeting.mRecyclerView.setAdapter(mFragmentListMeeting.mAdapter);
    }

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

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            filtrer.set(year, monthOfYear, dayOfMonth);
            Log.d("Debug", "onDateSet: " + filtrer);
            initListWithFilter(mApiService.FilterByDate(filtrer));
        }
    };

    void AlerteDialogSpinner() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
        mBuilder.setTitle("Selectionner la salle de réunion que vous souhaitez voire");

        Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner_dialog); // mView for shearch in layout dialog_spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.meeting_location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initListWithFilter(mApiService.FilterByLocation(mSpinner.getSelectedItem().toString()));
                dialog.dismiss();
            }
        });
        mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mApiService.onDestroy();
    }
}