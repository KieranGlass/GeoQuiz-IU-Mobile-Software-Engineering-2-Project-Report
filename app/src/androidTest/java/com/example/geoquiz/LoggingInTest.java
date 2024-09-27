package com.example.geoquiz;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
public class LoggingInTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void testSuccessfulLogin() {

        // Perform login action with correct credentials
        onView(withId(R.id.loginUsername)).perform(typeText("master"));
        onView(withId(R.id.loginPassword)).perform(typeText("1"));
        onView(withId(R.id.enterBtn)).perform(click());

        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidLogin() {
        // Perform login action with incorrect credentials
        onView(withId(R.id.loginUsername)).perform(typeText("wrongUser"));
        onView(withId(R.id.loginPassword)).perform(typeText("wrongPass"));
        onView(withId(R.id.enterBtn)).perform(click());

        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        Intents.release();
    }
}