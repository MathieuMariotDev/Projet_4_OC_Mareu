package com.example.mareu.meeting_list;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.R;
import com.example.mareu.modele.Meeting;
import com.example.mareu.service.DummyMeetingApiService;
import com.example.mareu.service.MeetingApiService;
import com.example.mareu.ui.MainActivity;
import com.example.mareu.ui.RecyclerViewItemCountAssertion;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import android.view.WindowManager;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class Filter {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void Setup(){
        // Creat First Meeting
        onView(withId(R.id.BtnAddMeeting)).perform(click());
        onView(withId(R.id.subject_meeting)).perform(replaceText("Réunion A"),closeSoftKeyboard());
        onView(withId(R.id.participants_email)).perform(replaceText("math.mariot@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.participants_email)).perform(replaceText("jeanpaul@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.BtnTimePickersStart)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(8,30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.BtnDatePicker)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020,4,02));

        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create)).perform(click());
        //Creat Second Meeting
        onView(withId(R.id.BtnAddMeeting)).perform(click());
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)),is("Réunion 3"))).perform(click());
        onView(withId(R.id.subject_meeting)).perform(replaceText("Réunion PEACH"),closeSoftKeyboard());
        onView(withId(R.id.participants_email)).perform(replaceText("math.mariot@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.participants_email)).perform(replaceText("jeanpaul@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.BtnTimePickersStart)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(8,30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.BtnDatePicker)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020,4,03));

        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create)).perform(click());
    }


    @Test
    public void FilterByDate(){
        ///////////////////////////Filter by date ////////
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title),withText("Filtre par date"))).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020,4,03));  ///Filter by date
        onView(withId(android.R.id.button1)).perform(click());
        /////////////////////////////////Looking if juste 1meeting is display ///
        onView(withId(R.id.recycler_view_main)).check(new RecyclerViewItemCountAssertion(1)); //check if recylcler view only display 1meeting
        onView(withId(R.id.item_list)).check(matches(not(withText("Réunion 1 - 08:30 - Réunion A"))));
        onView(withId(R.id.item_list)).check(matches(withText("Réunion 3 - 08:30 - Réunion PEACH")));//check if good meeting is display
    }

    @Test
    public void FilterByLocation(){
        onView(withContentDescription("More options")).perform(click());
        onView(allOf(withId(R.id.title),withText("Filtre par lieu"))).perform(click());
        onView(withId(R.id.spinner_dialog)).perform(click());
        onData(allOf(is(instanceOf(String.class)),
                is("Réunion 3")))
                .inRoot(isPlatformPopup())
                .perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.recycler_view_main)).check(new RecyclerViewItemCountAssertion(1)); //check if recylcler view only display 1meeting
        onView(withId(R.id.item_list)).check(matches(not(withText("Réunion 1 - 08:30 - Réunion A"))));
        onView(withId(R.id.item_list)).check(matches(withText("Réunion 3 - 08:30 - Réunion PEACH"))); //check if good meeting is display
    }
}
