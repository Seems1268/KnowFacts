package com.example.knowfacts

import com.example.knowfacts.model.Facts
import com.example.knowfacts.model.Info
import com.example.knowfacts.service.FactsDataService
import com.example.knowfacts.ui.KnowFactsActivity
import io.reactivex.Observable
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


/**
 * Created by Seema Savadi on 01/09/20.
 */

@RunWith(RobolectricTestRunner::class)
class RetrofitCallTest {

    private lateinit var activity: KnowFactsActivity

    @Mock
    private lateinit var mockFactsDataService: FactsDataService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val controller = Robolectric.buildActivity(
            KnowFactsActivity::class.java
        )
        activity = controller.get()

        controller.create()
    }

    @Test
    @Throws(Exception::class)
    fun test_shouldFillAdapter() {

        val facts = Facts("About India", listOf(Info("Region", "It's a tropical region", null)))

        Mockito.`when`(mockFactsDataService.getFactsList()).thenReturn(Observable.just(facts))

        var adapter: FactsListAdapter? = null
        facts.rows?.let { rows ->
            adapter = FactsListAdapter(activity)
            adapter?.let { adapter ->
                adapter.facts = rows
            }
        }

        assertThat(
            "Adapter has correct number of facts.",
            adapter?.itemCount ?: 0 == facts.rows?.size ?: 0
        )
    }

    @Test
    @Throws(Exception::class)
    fun test_NoFactsData() {
        val facts = Facts("", null)

        Mockito.`when`(mockFactsDataService.getFactsList()).thenReturn(Observable.just(facts))

        var adapter: FactsListAdapter? = null
        facts.rows?.let { rows ->
            adapter = FactsListAdapter(activity)
            adapter?.let { adapter ->
                adapter.facts = rows
            }
        }

        assertThat("Adapter has no data", adapter == null)
    }

}