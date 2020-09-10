package com.example.knowfacts.ui

/**
 * Created by Seema Savadi on 27/08/20.
 */

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.knowfacts.R
import timber.log.Timber

/**
 * This is a single Activity application which uses Navigation library and content is displayed by the fragments.
 */

class KnowFactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_know_facts)
        Timber.i("OnCreate")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Timber.i("OnBackPressed")
        finish()
    }
}
