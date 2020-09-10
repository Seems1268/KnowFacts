package com.example.knowfacts.repository

import androidx.lifecycle.MutableLiveData
import com.example.knowfacts.model.Facts
import com.example.knowfacts.service.KnowFactsNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Seema Savadi on 09/09/20.
 */

/**
 * Repository for fetching facts from the network.
 */
class FactsRepository {

    private var disposable: Disposable? = null
    var factsData = MutableLiveData<Facts>()
    var isNetWorkError = MutableLiveData<Boolean>().apply { false }


    fun refreshFacts() {
        disposable =
            KnowFactsNetwork.knowfacts.getFactsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        response?.let {
                            factsData.postValue(it)
                            isNetWorkError.value = false
                            Timber.d("fact title is %s", factsData.value?.title)
                            Timber.d(factsData.value.toString())
                        }
                    },
                    { error ->
                        error?.let {
                            Timber.e(it)
                            factsData.postValue(null)
                            isNetWorkError.value = true
                        }
                    }
                )
    }

    fun clear() {
        disposable?.dispose()
    }
}