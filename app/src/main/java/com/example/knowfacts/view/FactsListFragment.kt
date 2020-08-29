package com.example.knowfacts.view

/**
 * Created by Seema Savadi on 28/08/20.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knowfacts.App
import com.example.knowfacts.FactsListAdapter
import com.example.knowfacts.R
import com.example.knowfacts.databinding.FactsListFragmentBinding
import com.example.knowfacts.model.Facts
import timber.log.Timber

/**
 * Fragment presents the main UI of the application i.e. recyclerview with facts list data.
 */
class FactsListFragment : Fragment() {

    private lateinit var binding: FactsListFragmentBinding
    private lateinit var viewModel: FactsListViewModel
    private var factsData: Facts? = null
    private lateinit var factsAdapter: FactsListAdapter

    companion object {
        @JvmStatic
        fun newInstance() = FactsListFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.i("onCreateView")

        binding = DataBindingUtil.inflate(inflater, R.layout.facts_list_fragment, container, false)
        binding.lifecycleOwner = this

        val factory: ViewModelProvider.Factory = AndroidViewModelFactory(App())

        viewModel = ViewModelProvider(this, factory).get(FactsListViewModel::class.java).apply {

            lifecycle.addObserver(this)
        }

        getFactsList()

        with(binding.swipeLayout) {
            setColorSchemeResources(R.color.colorPrimary)
            setOnRefreshListener { getFactsList() }
        }

        return binding.root
    }

    /**
     * Observes the facts data fetched from ViewModel and updates the UI.
     */
    private fun getFactsList() {

        viewModel.fetchFactsData().observe(viewLifecycleOwner, { facts: Facts? ->

            facts?.let {
                factsData = it
                Timber.d("got the data into frag %s", factsData?.rows?.get(0)?.description)
                binding.swipeLayout.isRefreshing = false
                initUI()
            } ?: run {
                initUI()
            }

        })
    }

    /**
     * Method initializes the recyclerview if the facts data is available using adapter else displays the error message.
     */
    private fun initUI() {

        factsData?.let { facts ->

            binding.swipeLayout.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.GONE

            activity?.title = facts.title

            activity?.let {
                factsAdapter = FactsListAdapter(facts.rows, it)

                with(binding.recyclerview) {
                    layoutManager = LinearLayoutManager(activity)
                    itemAnimator = DefaultItemAnimator()
                    adapter = factsAdapter
                }

                factsAdapter.notifyDataSetChanged()
            }

        } ?: run {
            binding.swipeLayout.isRefreshing = false
            binding.swipeLayout.visibility = View.GONE
            binding.errorMessage.visibility = View.VISIBLE
        }

    }
}