package com.example.knowfacts.service

import com.example.knowfacts.model.Facts
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface FactsDataService {

    @GET("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json")
    fun getFactsList(): Observable<Facts>

}