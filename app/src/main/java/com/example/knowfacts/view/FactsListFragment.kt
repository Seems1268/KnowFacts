package com.example.knowfacts.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.example.knowfacts.App
import com.example.knowfacts.R
import com.example.knowfacts.databinding.FactsListFragmentBinding

class FactsListFragment : Fragment() {

    private lateinit var binding: FactsListFragmentBinding
    private lateinit var viewModel: FactsListViewModel


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

        binding.swipeLayout.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeLayout.setOnRefreshListener { getFactsList() }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory: ViewModelProvider.Factory = AndroidViewModelFactory(App())

        viewModel = ViewModelProvider(this, factory).get(FactsListViewModel::class.java).apply {

            lifecycle.addObserver(this)
        }

    }

    private fun getFactsList() {

    }

}