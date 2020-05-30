package com.kat.weather.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Connection with retrofit
 */
object ApiUtils {
    private var BASEURL:String="https://api.darksky.net/forecast/"
    fun callWebService(): ApiInterface {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASEURL)
            .build()

        return retrofit.create(ApiInterface::class.java)
    }
}