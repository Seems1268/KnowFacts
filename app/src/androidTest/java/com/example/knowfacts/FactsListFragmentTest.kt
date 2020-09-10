package com.example.knowfacts


import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.knowfacts.network.NetworkUtil
import com.example.knowfacts.ui.KnowFactsActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Seema Savadi on 29/08/20.
 */

@RunWith(AndroidJUnit4::class)
class FactsListFragmentTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(KnowFactsActivity::class.java)


    @Test
    fun test_fragmentLaunchedSuccessfully() {
        onView(withId(R.id.mainLayout)).check(matches(isDisplayed()))
        onView((withId(R.id.swipe_layout))).check(matches(isDisplayed()))
    }

    @Test
    fun test_factsListDisplayed() {
        if (NetworkUtil.isInternetAvailable(activityTestRule.activity)) {

            onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun test_netWorkErrorDisplayed() {
        if (!NetworkUtil.isInternetAvailable(activityTestRule.activity)) {
            onView(withId(R.id.errorMessage)).check(matches(withText(R.string.no_network_error_message)))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun test_swipeRefreshLayout() {
        onView((withId(R.id.swipe_layout))).perform(
            withCustomConstraints(
                swipeDown(),
                isDisplayingAtLeast(85)
            )
        )
    }

    private fun withCustomConstraints(action: ViewAction, constraints: Matcher<View>): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return constraints
            }

            override fun getDescription(): String {
                return action.description
            }

            override fun perform(uiController: UiController?, view: View?) {
                action.perform(uiController, view)
            }
        }
    }
}