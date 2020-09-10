package com.example.knowfacts.viewmodels

/**
 * Created by Seema Savadi on 28/08/20.
 */

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.knowfacts.network.NetworkUtil
import com.example.knowfacts.repository.FactsRepository
import timber.log.Timber

/**
 * ViewModel class to fetch facts data using Network APIs.
 */
class FactsListViewModel(context: Application) : AndroidViewModel(context), LifecycleObserver {


    /**
     * The data source this ViewModel will fetch results from.
     */
    private val repository = FactsRepository()

    val facts = repository.factsData

    val isNetworkError = repository.isNetWorkError

    private val context = getApplication<Application>().applicationContext


    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        refreshDataFromRepository()
    }


    /**
     * Refresh data from the repository.
     */
    fun refreshDataFromRepository() {
        if (NetworkUtil.isInternetAvailable(context)) {
            repository.refreshFacts()
        } else {
            isNetworkError.value = true
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.i("onPause")
        repository.clear()
    }

    /**
     * Factory for constructing FactsListViewModel with parameter
     */

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FactsListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FactsListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}