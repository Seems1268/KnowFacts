package com.example.knowfacts.view

/**
 * Created by Seema Savadi on 28/08/20.
 */

import android.app.Application
import androidx.lifecycle.*
import com.example.knowfacts.model.Facts
import com.example.knowfacts.service.RetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * ViewModel class to fetch facts data using Network APIs.
 */
class FactsListViewModel(context: Application) : AndroidViewModel(context), LifecycleObserver {

    private var disposable: Disposable? = null
    private var factsData = MutableLiveData<Facts>()

    /**
     * Instantiates one time service object.
     */
    private val getFactsDataService by lazy {
        RetrofitInstance.getService()
    }

    /**
     * Fetches the facts data using Retrofit Network library.
     */
    fun fetchFactsData(): MutableLiveData<Facts> {

        disposable =
            getFactsDataService.getFactsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        response?.let {
                            factsData.value = it
                            Timber.d("fact title is %s", factsData.value?.title)
                            Timber.d(factsData.value.toString())
                        }
                    },
                    { error ->
                        error?.let {
                            Timber.e(it)
                        }
                    }
                )

        return factsData
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.i("onPause")
        disposable?.dispose()
    }


}