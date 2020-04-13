package com.example.mareu.meeting_list;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import android.view.WindowManager;
@LargeTest
@RunWith(AndroidJUnit4ClassRunner.class)
public class MeetingListTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void Setup(){
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
        onView(withId(R.id.create)).perform(click());
    }
    @Test
    public void CreateMeeting() { //Create and display correct meeting
            onView(withId(R.id.item_list)).check(matches(withText("Réunion 1 - 08:30 - Réunion A")));
            onView(withId(R.id.item_list_mail)).check(matches(withText("math.mariot@gmail.com, jeanpaul@gmail.com")));
    }

@Test
    public void DestoyListOnRotation(){
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.recycler_view_main)).check(new RecyclerViewItemCountAssertion(0));
}

@Test
    public void DeleteOnClick(){
        onView(withId(R.id.item_list_delete)).perform(click());
    onView(withId(R.id.recycler_view_main)).check(new RecyclerViewItemCountAssertion(0));
}
}