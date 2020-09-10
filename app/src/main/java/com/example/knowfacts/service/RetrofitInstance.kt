package com.example.knowfacts.service

/**
 * Created by Seema Savadi on 28/08/20.
 */

import com.example.knowfacts.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Main entry point for network access. Call like `KnowFactsNetwork.knowfacts.getFactsList()`
 */
object KnowFactsNetwork {

    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .build()

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val knowfacts: FactsDataService = retrofit.create(FactsDataService::class.java)
}