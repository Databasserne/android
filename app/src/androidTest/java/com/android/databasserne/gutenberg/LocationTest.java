package com.android.databasserne.gutenberg;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by kasper on 5/13/17.
 */

@RunWith(AndroidJUnit4.class)
public class LocationTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class, true, false);

    String location = "lat long";

    @Before
    public void setup() throws Exception {
        super.setUp();
        Intent intent = new Intent();
        mainActivity.launchActivity(intent);
    }

    @Test
    public void testTabsNavigation() {

        onView(withId(R.id.tabs))
                .check(matches(isDisplayed()));
        onView(withId(R.id.viewpager))
                .check(matches(isDisplayed()));
        onView(withText("Location")).perform(click());

        onView(withId(R.id.locationInsert))
                .perform(typeText(location));

        onView(withId(R.id.locationButton))
                .perform(click());

        onView(withId(R.id.locationResults))
                .check(matches(isDisplayed()));
    }


}