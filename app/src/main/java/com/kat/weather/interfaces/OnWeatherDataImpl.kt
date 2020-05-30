package com.kat.weather.interfaces

import com.kat.weather.model.ResponseData

interface OnWeatherDataImpl {
    fun onSuccess(data: List<ResponseData>)
    fun onFailure(error: String)
}