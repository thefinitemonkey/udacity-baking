package com.example.a0603614.udacity_baking;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StepsListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(
            RecipeListActivity.class);

    @Test
    public void stepsListActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(
                        withId(R.id.rv_recipe_list),
                        childAtPosition(
                                withId(R.id.cl_recipe_list),
                                0
                        )
                ));
        recyclerView.perform(actionOnItemAtPosition(0, click()));


        ViewInteraction recyclerView3 = onView(
                allOf(
                        withId(R.id.rv_recipe_steps),
                        childAtPosition(
                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                0
                        )
                ));
        recyclerView3.perform(actionOnItemAtPosition(2, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_step_details_text),
                      withText("1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan."),
                      childAtPosition(
                              allOf(
                                      withId(R.id.cl_step_details),
                                      childAtPosition(
                                              withId(R.id.cl_details_display),
                                              0
                                      )
                              ),
                              0
                      ),
                      isDisplayed()
                ));
        textView.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}