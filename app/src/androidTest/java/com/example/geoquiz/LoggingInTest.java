package com.example.geoquiz;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;



@RunWith(AndroidJUnit4.class)
public class LoggingInTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public ActivityScenarioRule<QuizDashboard> quizDashboardRule = new ActivityScenarioRule<>(QuizDashboard.class);
    @Before
    public void setUp() {

    }

    @Test
    public void testSuccessfulLogin() {

        try {

            User admin = new User();
            admin.setId(1);
            admin.setUsername("master");
            admin.setPassword("1");

            // Perform login action with correct credentials
            onView(withId(R.id.loginUsername)).perform(typeText(admin.getUsername()));
            onView(withId(R.id.loginPassword)).perform(typeText(admin.getPassword()));
            onView(withId(R.id.enterBtn)).perform(click());

            // Simulate the login success
            UserLogin.setCurrentUser(admin);

            // Wait for the activity to load

            ActivityScenario<QuizDashboard> scenario = ActivityScenario.launch(QuizDashboard.class);

            scenario.onActivity(activity -> {
                // Verify the logged-in user

                String greeting = activity.tvUsername.getText().toString();
                assertEquals(greeting, "Welcome master");

                // Verify other UI elements
                assertNotNull(activity.findViewById(R.id.tvFlagCompletion));
                assertNotNull(activity.findViewById(R.id.tvCapitalCompletion));
                assertNotNull(activity.findViewById(R.id.tvLandmarkCompletion));
                assertNotNull(activity.findViewById(R.id.tvFoodCompletion));
                assertNotNull(activity.findViewById(R.id.tvSportsCompletion));
                assertNotNull(activity.findViewById(R.id.tvBrandCompletion));

            });
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }


    @Test
    public void testInvalidLogin() {

        try {

            // Perform login action with incorrect credentials
            onView(withId(R.id.loginUsername)).perform(typeText("wrongUser"));
            onView(withId(R.id.loginPassword)).perform(typeText("wrongPass"));
            onView(withId(R.id.enterBtn)).perform(click());

            assertFalse(MainActivity.formFilledCorrectly);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }

    }
}