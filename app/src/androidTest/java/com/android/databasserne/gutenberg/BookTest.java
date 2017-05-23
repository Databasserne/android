package com.android.databasserne.gutenberg;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.android.databasserne.gutenberg.utils.RestJSONHelper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

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
public class BookTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(MainActivity.class, true, false);

    private String book = "La Fiammetta";
    private MockWebServer server;

    @Before
    public void setup() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInsrumentation(InstrumentationRegistry.getInstrumentation());
        Intent intent = new Intent();
        mainActivity.launchActivity(intent);
        PageFragment.server = server.url("").toString();
    }

    @Test
    public void testBookCall() throws Exception {

        String fileName = "bookTest.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestJSONHelper.getStringFromFile(getInstrumentation().getTargetContext(), fileName)));

        onView(withId(R.id.tabs))
                .check(matches(isDisplayed()));
        onView(withId(R.id.viewpager))
                .check(matches(isDisplayed()));
        onView(withText("Book")).perform(click());

        onView(withId(R.id.bookInsert))
                .perform(typeText(book));

        onView(withId(R.id.bookButton))
                .perform(click());

        onView(withId(R.id.bookMap))
                .check(matches(isDisplayed()));

    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }



}
