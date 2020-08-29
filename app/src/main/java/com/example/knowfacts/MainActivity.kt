package com.example.knowfacts

/**
 * Created by Seema Savadi on 27/08/20.
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.knowfacts.view.FactsListFragment
import timber.log.Timber

/**
 * Main launcher activity and starting point for the application.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.i("OnCreate")

        addFragment()
    }

    /**
     * Adds fragment to display the facts list.
     */
    private fun addFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, FactsListFragment.newInstance())
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Timber.i("OnBackPressed")
        finish()
    }
}
