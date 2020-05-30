package com.kat.weather.network

/**
 * Connector for all APIs call
 */
class ApiCalls {

    private val apiInterface: ApiInterface

    init {
        apiInterface = ApiUtils.callWebService()
    }

    fun fetchWeather(lat: String, lng: String) = apiInterface.getWeather(lat, lng)

}