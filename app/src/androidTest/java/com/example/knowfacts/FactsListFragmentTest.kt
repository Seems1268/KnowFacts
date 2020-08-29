package com.example.knowfacts

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.knowfacts.view.FactsListFragment
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Seema Savadi on 29/08/20.
 */

@RunWith(AndroidJUnit4::class)
class FactsListFragmentTest {


    @Test
    fun test_FragmentLaunch() {
        val scenario = launchFragmentInContainer<FactsListFragment>(
            null, -1, FragmentFactory()
        )
        onView(withId(R.id.mainLayout)).check(matches(isDisplayed()))
    }

}