package com.example.knowfacts.service

/**
 * Created by Seema Savadi on 28/08/20.
 */

import com.example.knowfacts.model.Facts
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retrofit service interface defines the different API end points.
 */
interface FactsDataService {

    @GET("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json")
    fun getFactsList(): Observable<Facts>

}