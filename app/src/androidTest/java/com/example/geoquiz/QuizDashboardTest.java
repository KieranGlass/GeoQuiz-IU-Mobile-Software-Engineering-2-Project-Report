package com.example.geoquiz;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class QuizDashboardTest {
    private DatabaseHelper mockHelper;

    @Rule
    public ActivityScenarioRule<QuizDashboard> rule =
            new ActivityScenarioRule<>(QuizDashboard.class);
    @Before
    public void setup() {

        Context app = ApplicationProvider.getApplicationContext();
        mockHelper = new DatabaseHelper(app, null, null, DatabaseHelper.DB_VERSION);

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
