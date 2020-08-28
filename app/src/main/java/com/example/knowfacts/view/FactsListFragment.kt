package com.example.knowfacts.view

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

class FactsListFragment : Fragment() {

    private lateinit var binding: FactsListFragmentBinding
    private lateinit var viewModel: FactsListViewModel
    private lateinit var factsData: Facts
    private lateinit var factsAdapter: FactsListAdapter

    companion object {
        @JvmStatic
        fun newInstance() = FactsListFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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


    private fun getFactsList() {

        viewModel.fetchFactsData().observe(viewLifecycleOwner, { facts: Facts? ->

            facts?.let {
                factsData = it
                Timber.d("got the data into frag " + factsData.rows[0].description)
                binding.swipeLayout.isRefreshing = false
                initUI()
            }

        })

    }

    private fun initUI() {

        activity?.title = factsData.title

        factsAdapter = FactsListAdapter(factsData.rows)

        with(binding.recyclerview) {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            adapter = factsAdapter
        }

        factsAdapter.notifyDataSetChanged()

    }


}