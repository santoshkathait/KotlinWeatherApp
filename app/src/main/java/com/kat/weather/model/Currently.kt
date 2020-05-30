package com.kat.weather.model

data class Currently(
    var time: Int,
    var summary: String,
    var icon: String,
    var nearestStormDistance: Int,
    var nearestStormBearing: Int,
    var precipIntensity: Double,
    var precipProbability: Double,
    var temperature: Double,
    var apparentTemperature: Double,
    var dewPoint: Double,
    var humidity: Double,
    var pressure: Double,
    var windSpeed: Double,
    var windGust: Double,
    var windBearing: Double,
    var cloudCover: Double,
    var uvIndex: Double,
    var visibility: Double,
    var ozone: Double

)
