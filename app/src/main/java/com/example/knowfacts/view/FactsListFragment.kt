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
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.knowfacts.FactsListAdapter
import com.example.knowfacts.R
import com.example.knowfacts.databinding.FactsListFragmentBinding
import com.example.knowfacts.model.Facts
import com.example.knowfacts.network.NetworkUtil
import timber.log.Timber


/**
 * Fragment presents the main UI of the application i.e. recyclerview with facts list data.
 */
class FactsListFragment : Fragment() {

    private lateinit var binding: FactsListFragmentBinding
    private var factsData: Facts? = null
    private lateinit var factsAdapter: FactsListAdapter

    private val viewModel by viewModels<FactsListViewModel>()

    private var errorMessage = MutableLiveData<String>().apply {
        value = ""
    }

    companion object {
        @JvmStatic
        fun newInstance() = FactsListFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.i("onCreateView")

        lifecycle.addObserver(viewModel)


        binding = DataBindingUtil.inflate(inflater, R.layout.facts_list_fragment, container, false)
        binding.lifecycleOwner = this


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

        context?.let {
            if (NetworkUtil.isInternetAvailable(it)) {
                viewModel.fetchFactsData()

                viewModel.factsData.observe(viewLifecycleOwner, { facts: Facts? ->

                    facts?.let {
                        factsData = it
                        Timber.d("got the data into frag %s", factsData?.rows?.get(0)?.description)
                        binding.swipeLayout.isRefreshing = false
                        initUI()

                    } ?: run {
                        errorMessage.value = it.getString(R.string.error_message)
                        displayError()
                    }

                })
            } else {
                errorMessage.value = it.getString(R.string.no_network_error_message)
                displayError()
            }
        }
    }

    /**
     * Method initializes the recyclerview if the facts data is available using adapter else displays the error message.
     */
    private fun initUI() {

        factsData?.let { facts ->

            binding.recyclerview.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.GONE

            activity?.title = facts.title

            activity?.let {
                facts.rows?.let { rows ->
                    factsAdapter = FactsListAdapter(rows, it)

                    with(binding.recyclerview) {
                        layoutManager = LinearLayoutManager(activity)
                        itemAnimator = DefaultItemAnimator()
                        adapter = factsAdapter
                    }

                    factsAdapter.notifyDataSetChanged()
                } ?: run {
                    errorMessage.value = it.getString(R.string.error_message)
                    displayError()
                }
            }
        }
    }

    private fun displayError() {
        binding.swipeLayout.isRefreshing = false
        binding.recyclerview.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = errorMessage.value
    }
}