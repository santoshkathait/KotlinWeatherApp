package com.kat.weather.model

data class Minutely(
    var summary: String,
    var icon: String,
    var data: List<Data>
)