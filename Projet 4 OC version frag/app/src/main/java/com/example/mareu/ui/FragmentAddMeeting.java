package com.example.mareu.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAddMeeting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddMeeting extends Fragment implements /*TimePickerDialog.OnTimeSetListener/*, DatePickerDialog.OnDateSetListener,*/ AdapterView.OnItemSelectedListener {

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
    public Calendar CalendarMeeting = Calendar.getInstance();
    public String Time;
    private MeetingApiService mApiService = new DummyMeetingApiService();
    public Calendar CalendarEndMeeting = Calendar.getInstance();
    private int ColorMeeting;

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
    }

    @OnClick(R.id.BtnTimePickersStart)
    void OpenTimePicker(View v) {
        //DialogFragment timePickerFragment = new TimePickerFragment();
        // timePickerFragment.show(getChildFragmentManager(), "timePickerDebut");
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

   /* @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }*/

    TimePickerDialog.OnTimeSetListener onTimeStart = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            CalendarMeeting.set(Calendar.HOUR_OF_DAY, hourOfDay);
            CalendarMeeting.set(Calendar.MINUTE, minute);

        }
    };

    TimePickerDialog.OnTimeSetListener onTimeEnd = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            CalendarEndMeeting.set(Calendar.HOUR_OF_DAY, hourOfDay);
            CalendarEndMeeting.set(Calendar.MINUTE, minute);
            lockedTime();
        }
    };


    public void lockedTime() {
        List<Meeting> meetings = mApiService.getMeetingList();
        for (Meeting meeting : meetings) {

            if (((CalendarEndMeeting.get(Calendar.YEAR) == meeting.getDayTimeEndCalendar().get(Calendar.YEAR) && CalendarEndMeeting.get(Calendar.MONTH) == meeting.getDayTimeEndCalendar().get(Calendar.MONTH) && CalendarEndMeeting.get(Calendar.DAY_OF_MONTH) == meeting.getDayTimeEndCalendar().get(Calendar.DAY_OF_MONTH))) && meeting.getLocation().equals(Location)) {


                if (!((CalendarMeeting.before(meeting.getDayTimeCalendar()) || CalendarMeeting.after(meeting.getDayTimeEndCalendar())) && (CalendarEndMeeting.before(meeting.getDayTimeCalendar()) || CalendarEndMeeting.after(meeting.getDayTimeEndCalendar())))) {
                    //Toast.makeText(getActivity(), "Votre réunion empiète sur une autre.Veuillez de nouveau saisir une autre heure ou sélectionner une autre salle", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder ErrorMsg = new AlertDialog.Builder(getActivity());
                    ErrorMsg.setMessage("Votre réunion est sur un créneaux déjà réservé veuillez sélectionner une autre salle ou décaller la réunion en sélectionnant une heure de début et de fin valide")
                            .setTitle("Problème pour placer la réunion");
                    ErrorMsg.create();
                    ErrorMsg.show();

                }
            }
            Toast.makeText(getActivity(), "Votre réunion est correctement placé.Vous pouvez valider", Toast.LENGTH_LONG).show();
        }
    }



   /* @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        CalendarMeeting.set(year, month, dayOfMonth);
        Log.d("Debug", "onDateSet: " + CalendarMeeting);
    }*/

    @OnClick(R.id.Add_email)
    void BtnAddEmail() {
        Listemail.add(Email.getText().toString());
        Item_list_mail.setText(Listemail.toString());
        Email.getText().clear();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Location = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), Location, Toast.LENGTH_SHORT).show();
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
            Log.d("Debug", "onDateSetTest/////: " + CalendarMeeting);
        }
    };

    public int randomColor(){
        Random random= new Random();

        return ColorMeeting=Color.argb(255, random.nextInt(256), random.nextInt(256),
                random.nextInt(256));
    }
}
