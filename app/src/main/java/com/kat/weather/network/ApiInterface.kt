package com.kat.weather.network

import com.kat.weather.model.ResponseData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * All the request for APIs
 */
interface ApiInterface {

    @GET("900435862f097f4cc7a2021dd67b8c12/{lat},{lng}")
    fun getWeather(
        @Path("lat") lat: String,
        @Path("lng") lng: String
    ): Observable<ResponseData>


}