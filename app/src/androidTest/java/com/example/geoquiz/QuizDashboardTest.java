package com.example.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

public class QuizDashboardTest {

    private DatabaseHelper mockHelper;

    @Rule
    public ActivityScenarioRule<QuizDashboard> rule =
            new ActivityScenarioRule<>(QuizDashboard.class);
    @Before
    public void setup() {

        Context appContext = ApplicationProvider.getApplicationContext();
        mockHelper = new DatabaseHelper(appContext, null, null, DatabaseHelper.DB_VERSION);

    }

    @Test
    public void testUserCompletion(){

        rule.getScenario().onActivity(activity -> {
            User user = new User();
            user.setId(1);
            user.setUsername("master");
            user.setPassword("1");

            DatabaseHelperWrapper dbWrapper = new DatabaseHelperWrapper(mockHelper);

            List<UserProgress> returnedUserScoresBrands = dbWrapper.getUserProgressByCategory(user.getId(), 1);
            List<UserProgress> returnedUserScoresSports = dbWrapper.getUserProgressByCategory(user.getId(), 2);
            List<UserProgress> returnedUserScoresFoods = dbWrapper.getUserProgressByCategory(user.getId(), 3);
            List<UserProgress> returnedUserScoresLandmarks = dbWrapper.getUserProgressByCategory(user.getId(), 4);
            List<UserProgress> returnedUserScoresCapitals = dbWrapper.getUserProgressByCategory(user.getId(), 5);
            List<UserProgress> returnedUserScoresFlags = dbWrapper.getUserProgressByCategory(user.getId(), 6);

            if (returnedUserScoresBrands.size() >= 1) {
                assertNotEquals("0", activity.tvSportsCompletion.toString());
                Log.i("testUserCompletion", "Scores Detected");
            } else {
                Log.i("testUserCompletion", "No Scores present for user!");
            }
            if (returnedUserScoresSports.size() >= 1) {
                assertNotEquals("0", activity.tvSportsCompletion.toString());
                Log.i("testUserCompletion", "Scores Detected");
            } else {
                Log.i("testUserCompletion", "No Scores present for user!");
            }
            if (returnedUserScoresFoods.size() >= 1) {
                assertNotEquals("0", activity.tvSportsCompletion.toString());
                Log.i("testUserCompletion", "Scores Detected");
            } else {
                Log.i("testUserCompletion", "No Scores present for user!");
            }
            if (returnedUserScoresLandmarks.size() >= 1) {
                assertNotEquals("0", activity.tvSportsCompletion.toString());
                Log.i("testUserCompletion", "Scores Detected");
            } else {
                Log.i("testUserCompletion", "No Scores present for user!");
            }
            if (returnedUserScoresCapitals.size() >= 1) {
                assertNotEquals("0", activity.tvSportsCompletion.toString());
                Log.i("testUserCompletion", "Scores Detected");
            } else {
                Log.i("testUserCompletion", "No Scores present for user!");
            }
            if (returnedUserScoresFlags.size() >= 1) {
                assertNotEquals("0", activity.tvSportsCompletion.toString());
                Log.i("testUserCompletion", "Scores Detected");
            } else {
                Log.i("testUserCompletion", "No Scores present for user!");
            }
        });
    }

}
