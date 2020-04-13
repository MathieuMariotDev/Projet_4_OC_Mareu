package com.example.mareu.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mareu.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;


public class DatePickerFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener onDateSet;
    //private boolean isModal = false;

   /* public interface DatePickerFragmentListener {
        public void onDateSet(Calendar calendar);
    }*/

    public static DatePickerFragment newIntance() {
        DatePickerFragment frag = new DatePickerFragment();
        //frag.isModal = true;
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), onDateSet, year, month,
                date);
    }//dateSetListener(DatePickerDialog.OnDateSetListener) getParentFragment()


    /*private DatePickerDialog.OnDateSetListener dateSetListener =
         new DatePickerDialog.OnDateSetListener() {
     @Override
             public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                 CalendarMeeting.set(view.getYear(), view.getMonth(), view.getDayOfMonth());

                 Log.d("Debug", "onDateSet: " + CalendarMeeting);
             }
         };
*/
    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        onDateSet = onDate;
    }

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isModal) {
            return super.onCreateView(inflater, container, savedInstanceState);
        } else {
            View rootView = inflater.inflate(R.layout.fragment_add_meeting, container, false);
            return rootView;
        }
    }*/

}