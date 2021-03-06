package com.android.databasserne.gutenberg;

import android.content.Intent;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by kasper on 5/13/17.
 */

@RunWith(AndroidJUnit4.class)
public class TabsDisplayedTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class, true, false);

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

        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.fragment2)).check(matches(withText("book")));

        //Click instead, since we would otherwise just swipe on the map, resulting in error.
        onView(withText("Author")).perform(click());
        onView(withId(R.id.fragment3)).check(matches(withText("author")));

        onView(withId(R.id.viewpager)).perform(swipeLeft());
        onView(withId(R.id.fragment4)).check(matches(withText("location")));

        onView(withText("City")).perform(click());
        onView(withId(R.id.fragment)).check(matches(withText("city")));

    }


}
