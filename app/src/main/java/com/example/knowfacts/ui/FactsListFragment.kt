package com.example.knowfacts.ui

/**
 * Created by Seema Savadi on 28/08/20.
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowfacts.FactsListAdapter
import com.example.knowfacts.R
import com.example.knowfacts.databinding.FactsListFragmentBinding
import com.example.knowfacts.model.Facts
import com.example.knowfacts.viewmodels.FactsListViewModel
import timber.log.Timber


/**
 * Fragment presents the main UI of the application i.e. recyclerview with facts list data.
 */
class FactsListFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: FactsListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, FactsListViewModel.Factory(activity.application))
            .get(FactsListViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of Video to cards.
     */
    private var factsAdapter: FactsListAdapter? = null

    private lateinit var binding: FactsListFragmentBinding
    private var factTitle = ""


    private var errorMessage = MutableLiveData<String>().apply {
        value = ""
    }


    /**
     * Called immediately after onCreateView() has returned, and fragment's
     * view hierarchy has been created. It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.facts.observe(viewLifecycleOwner, { facts: Facts? ->
            facts?.apply {
                facts.title?.apply {
                    factTitle = this
                }
                facts.rows?.let {
                    factsAdapter?.facts = it
                    initUI()
                } ?: run {
                    errorMessage.value = getString(R.string.error_message)
                }
            }
        })
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.i("onCreateView")

        lifecycle.addObserver(viewModel)

        binding = DataBindingUtil.inflate(inflater, R.layout.facts_list_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        context?.let {
            factsAdapter = FactsListAdapter(it)

            binding.root.findViewById<RecyclerView>(R.id.recyclerview).apply {
                layoutManager = LinearLayoutManager(context)
                adapter = factsAdapter
            }

            with(binding.swipeLayout) {
                setColorSchemeResources(R.color.colorPrimary)
                setOnRefreshListener { getFactsList() }
            }
        }

        // Observer for the network error.
        viewModel.isNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) {
                errorMessage.value = getString(R.string.no_network_error_message)
                displayError()
            }
        })


        return binding.root
    }


    /**
     * Observes the facts data fetched from ViewModel and updates the UI.
     */
    private fun getFactsList() {
        viewModel.refreshDataFromRepository()
    }

    /**
     * Method displays the facts list view.
     */
    private fun initUI() {

        binding.swipeLayout.isRefreshing = false
        binding.recyclerview.visibility = View.VISIBLE
        binding.errorMessage.visibility = View.GONE
        activity?.title = factTitle
    }

    /**
     * Method displays the error message in case of errors.
     */
    private fun displayError() {
        binding.swipeLayout.isRefreshing = false
        binding.recyclerview.visibility = View.GONE
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = errorMessage.value
        activity?.title = ""
    }
}