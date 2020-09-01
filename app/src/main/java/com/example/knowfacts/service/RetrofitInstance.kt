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
 * Initializes the retrofit object and creates an implementation of the API endpoints defined by the service interface.
 */
class RetrofitInstance {

    companion object {

        fun getService(): FactsDataService {

            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(FactsDataService::class.java)
        }
    }

}