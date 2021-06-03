package com.example.mareu.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.PatternMatcher;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddMeeting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddMeeting extends Fragment implements  AdapterView.OnItemSelectedListener {
    @BindView(R.id.spinner)
    Spinner LocationMeeting;
    private String Location;
    @BindView(R.id.subject_meeting)
    EditText Subject;
    @BindView(R.id.text_input_email)
    TextInputLayout Email;
    @BindView(R.id.Email_List)
    TextView Item_list_mail;
    @BindView(R.id.TimeStartDisplay)
    TextView timeStartDisplay;
    @BindView(R.id.TimeEndDisplay)
    TextView timeEndDisplay;
    @BindView(R.id.DateMeetingDisplay)
    TextView dateMeetingDisplay;
    private List<String> Listemail = new ArrayList<>();
    public Calendar CalendarMeeting = Calendar.getInstance();
    public MeetingApiService mApiService = new DummyMeetingApiService();
    public Calendar CalendarEndMeeting = Calendar.getInstance();
    private int ColorMeeting;
    Boolean Createauthorization;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.meeting_location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationMeeting.setAdapter(adapter);
        LocationMeeting.setOnItemSelectedListener(this);
        return view;
    }

    @OnClick(R.id.create)
    void AddMeeting() {
        Log.d("Debug", "AddMeetingFragment: " + CalendarMeeting);
        Createauthorization = mApiService.LockedTime(CalendarMeeting, CalendarEndMeeting, Location);
        if (Createauthorization) {


            Meeting meeting = new Meeting(
                    randomColor(),
                    CalendarMeeting,
                    CalendarEndMeeting,
                    Location,
                    Subject.getEditableText().toString(),
                    Listemail
            );
            mApiService.createMeeting(meeting);
            MainActivity.navigate(getContext());
        } else {
            ErrorLockedTime();
        }
    }

    @OnClick(R.id.BtnTimePickersStart)
    void OpenTimePicker(View v) {
        TimePickerFragment timePickerDialog = new TimePickerFragment().newIntance();
        timePickerDialog.setCallBack(onTimeStart);
        timePickerDialog.show(getParentFragmentManager().beginTransaction(), "TimePickerStart");
    }

    @OnClick(R.id.BtnTimePickersEnd)
    void OpenTimePickerEnd() {
        //DialogFragment timePickerFragment = new TimePickerFragment();
        //timePickerFragment.show(getChildFragmentManager(), "timePickerFin");
        TimePickerFragment timePickerDialog = new TimePickerFragment().newIntance();
        timePickerDialog.setCallBack(onTimeEnd);
        timePickerDialog.show(getParentFragmentManager().beginTransaction(), "TimePickerEnd");
    }

    @OnClick(R.id.BtnDatePicker)
    void OpenDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment().newIntance();
        datePickerFragment.setCallBack(onDate);
        datePickerFragment.show(getParentFragmentManager().beginTransaction(), "DatePickerFragment");
    }


    TimePickerDialog.OnTimeSetListener onTimeStart = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            CalendarMeeting.set(Calendar.HOUR_OF_DAY, hourOfDay);
            CalendarMeeting.set(Calendar.MINUTE, minute);
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
            String TimeStartString;
            TimeStartString = getString(R.string.TimeStartTextView) + dateFormat.format(CalendarMeeting.getTime());
            timeStartDisplay.setText(TimeStartString);


        }
    };

    TimePickerDialog.OnTimeSetListener onTimeEnd = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            CalendarEndMeeting.set(Calendar.HOUR_OF_DAY, hourOfDay);
            CalendarEndMeeting.set(Calendar.MINUTE, minute);
            DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRENCH);
            String TimeEndString;
            TimeEndString = getString(R.string.TimeEndTextView) + dateFormat.format(CalendarEndMeeting.getTime());
            timeEndDisplay.setText(TimeEndString);
        }
    };

    public void ErrorLockedTime() {
        AlertDialog.Builder ErrorMsg = new AlertDialog.Builder(getActivity());
        ErrorMsg.setMessage("Votre réunion est sur un créneaux déjà réservé veuillez sélectionner une autre salle ou décaller la réunion en sélectionnant une heure de début et de fin valide")
                .setTitle("Problème pour placer la réunion");
        ErrorMsg.create();
        ErrorMsg.show();
    }

    @OnClick(R.id.Add_email)
    void BtnAddEmail() {
        String emailInput = Email.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            Email.setError("Le champ est vide");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) { //  CTRL+b for see pattern
            Email.setError("Entrer un email valid");
        } else {
            Listemail.add(Email.getEditText().getText().toString().trim());//trim for remove space
            Email.getEditText().getText().clear();
            Item_list_mail.setText(Listemail.toString().replace("[", "").replace("]", ""));
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Location = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(), "Veuillez sélectionner un lieu de réunion", Toast.LENGTH_SHORT).show();
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            CalendarMeeting.set(year, monthOfYear, dayOfMonth);
            CalendarEndMeeting.set(year, monthOfYear, dayOfMonth);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
            String DateString;
            DateString = getString(R.string.DateStartTextView) + dateFormat.format(CalendarEndMeeting.getTime());
            dateMeetingDisplay.setText(DateString);
            Log.d("Debug", "onDateSetTest/////: " + CalendarMeeting);
        }
    };

    public int randomColor() {
        Random random = new Random();

        return ColorMeeting = Color.argb(255, random.nextInt(256), random.nextInt(256),
                random.nextInt(256));
    }
}
