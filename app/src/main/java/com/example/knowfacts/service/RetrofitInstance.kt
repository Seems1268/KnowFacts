package com.example.knowfacts.service

/**
 * Created by Seema Savadi on 28/08/20.
 */

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Initializes the retrofit object and creates an implementation of the API endpoints defined by the service interface.
 */
class RetrofitInstance {

    companion object {
        private val baseURL = "https://dl.dropboxusercontent.com/"
        private val connectionTimeout: Long = 120

        fun getService(): FactsDataService {

            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(connectionTimeout, TimeUnit.SECONDS)
                .readTimeout(connectionTimeout, TimeUnit.SECONDS)
                .writeTimeout(connectionTimeout, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(FactsDataService::class.java)
        }
    }

}