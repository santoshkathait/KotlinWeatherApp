package com.kat.weather.model

import java.io.Serializable

data class ResponseData(
    var latitude: Double,
    var longitude: Double,
    var timezone: String,
    val currently: Currently
//    var minutely: Minutely
//    var hourly: Hourly,
//    var daily: Daily,
//    var flag: Flag,
//    var offset: Int
): Serializable
