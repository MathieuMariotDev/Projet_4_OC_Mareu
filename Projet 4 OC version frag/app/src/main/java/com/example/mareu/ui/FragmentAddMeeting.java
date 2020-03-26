package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.modele.Meeting;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddMeeting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddMeeting extends Fragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner)
    Spinner LocationMeeting;
    private String Location;
    @BindView(R.id.subject_meeting)
    EditText Subject;
    @BindView(R.id.participants_email)
    EditText Email;
    @BindView(R.id.Email_List)
    TextView Item_list_mail;
    private List<String> Listemail = new ArrayList<>();
    private Calendar CalendarMeeting = Calendar.getInstance();
    public String Time;
    private MeetingApiService mApiService = new DummyMeetingApiService();

    public FragmentAddMeeting() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentAddMeeting newInstance() {
        FragmentAddMeeting fragment = new FragmentAddMeeting();
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
        View view = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        ButterKnife.bind(this, view);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.meeting_location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationMeeting.setAdapter(adapter);
        LocationMeeting.setOnItemSelectedListener(this);
        return view;
    }

    @OnClick(R.id.create)
    void AddMeeting() {
        Meeting meeting = new Meeting(

                CalendarMeeting,
                Location,
                Subject.getEditableText().toString(),
                Listemail
        );
        mApiService.createMeeting(meeting);
        MainActivity.navigate(getContext());
    }

    @OnClick(R.id.BtnDialogPickers)
    void OpenTimePicker(View v) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getChildFragmentManager(), "datePicker");
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getChildFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        CalendarMeeting.set(Calendar.HOUR_OF_DAY, hourOfDay);
        CalendarMeeting.set(Calendar.MINUTE, minute);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        CalendarMeeting.set(year, month, dayOfMonth);
        Log.d("Debug", "onDateSet: " + CalendarMeeting);
    }

    @OnClick(R.id.Add_email)// Changer en event ?
    void BtnAddEmail(){
        Listemail.add(Email.getText().toString());
        Item_list_mail.setText(Listemail.toString());
        Email.getText().clear();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Location = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), Location ,  Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Veuillez sélectionner un lieu de réunion" ,  Toast.LENGTH_SHORT).show();
    }
}
