package com.example.knowfacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.knowfacts.view.FactsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment()
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, FactsListFragment.newInstance())
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
