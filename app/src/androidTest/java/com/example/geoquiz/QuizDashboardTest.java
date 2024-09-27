package com.example.geoquiz;

import android.content.Intent;
import android.widget.Button;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class QuizDashboardTest {

    @Rule
    public ActivityTestRule<QuizDashboard> quizDashboardActivityTestRule =
            new ActivityTestRule<>(QuizDashboard.class);

    private Intent intent;

    @Before
    public void setup() {
        // Set up the intent to launch the activity
        intent = new Intent(ApplicationProvider.getApplicationContext(),
                MainActivity.class);
        intent.putExtra("username", "master");

    }

    @Test
    public void testUserNotLoggedIn() {
        // Launch the activity without login information
        quizDashboardActivityTestRule.launchActivity(intent);

        onView(withId(R.id.tvUsername)).check(matches(withText("Welcome")));
        onView(withId(R.id.flagBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.capitalBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.landmarkBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.foodBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.sportsBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.brandBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testUserLoggedIn() {
        // Launch the activity with login information
        quizDashboardActivityTestRule.launchActivity(intent);

        onView(withId(R.id.tvUsername)).check(matches(withText("Welcome master")));
        onView(withId(R.id.flagBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.capitalBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.landmarkBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.foodBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.sportsBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.brandBtn)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickFlagButton() {
        quizDashboardActivityTestRule.launchActivity(intent);

        onView(withId(R.id.flagBtn)).perform(click());
        onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickCapitalButton() {
        quizDashboardActivityTestRule.launchActivity(intent);

        onView(withId(R.id.capitalBtn)).perform(click());
        onView(withId(android.R.id.content)).check(matches(isDisplayed()));
    }
}
