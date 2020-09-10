package com.example.knowfacts

/**
 * Created by Seema Savadi on 27/08/20.
 */

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.knowfacts.ui.KnowFactsActivity
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = activityScenarioRule<KnowFactsActivity>()

    private lateinit var scenario: ActivityScenario<KnowFactsActivity>


    @Before
    fun setup() {

        scenario = rule.scenario
        assertNotNull(scenario)
    }

    @Test
    fun test_ActivityLaunched() {
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }

    @After
    fun cleanup() {
        scenario.close()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.knowfacts", appContext.packageName)
    }
}
