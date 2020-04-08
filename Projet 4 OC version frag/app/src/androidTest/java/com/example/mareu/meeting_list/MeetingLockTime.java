package com.example.mareu.meeting_list;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.R;
import com.example.mareu.ui.MainActivity;
import com.example.mareu.ui.RecyclerViewItemCountAssertion;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class MeetingLockTime {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        onView(withId(R.id.BtnAddMeeting)).perform(click());
        onView(withId(R.id.subject_meeting)).perform(replaceText("Réunion Projet 4"), closeSoftKeyboard());
        onView(withId(R.id.participants_email)).perform(replaceText("math.mariot@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.participants_email)).perform(replaceText("jeanpaul@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.BtnTimePickersStart)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(8, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.BtnTimePickersEnd)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(9, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.BtnDatePicker)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 4, 02));

        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create)).perform(click());
        //////////////////////////// Create Second Meeting/////////
        onView(withId(R.id.BtnAddMeeting)).perform(click());
        onView(withId(R.id.subject_meeting)).perform(replaceText("Réunion PEACH"), closeSoftKeyboard());
        onView(withId(R.id.participants_email)).perform(replaceText("math.mariot@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.participants_email)).perform(replaceText("jeanpaul@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.Add_email)).perform(click());
        onView(withId(R.id.BtnTimePickersStart)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(8, 45));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.BtnDatePicker)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 4, 02));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.BtnTimePickersEnd)).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(9, 29));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.create)).perform(click());


    }

    @Test
    public void LockedTime(){
        onView(allOf(IsInstanceOf.<View>instanceOf(android.widget.TextView.class), withText("Problème pour placer la réunion"))).check(matches(isDisplayed()));

    }
}
