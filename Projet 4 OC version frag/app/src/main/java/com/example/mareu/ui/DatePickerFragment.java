package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mareu.R;

import org.w3c.dom.Text;

import java.util.Calendar;


public class DatePickerFragment extends DialogFragment {
private Calendar CalendarMeeting = Calendar.getInstance();
private FragmentAddMeeting mFragmentAddMeeting;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), dateSetListener, year, month,
                date);
    }//(DatePickerDialog.OnDateSetListener) getParentFragment()


       private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
        @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    CalendarMeeting.set(view.getYear(), view.getMonth(), view.getDayOfMonth());

                    Log.d("Debug", "onDateSet: " + CalendarMeeting);
                }
            };





}