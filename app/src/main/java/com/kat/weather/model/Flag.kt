package com.kat.weather.model

import com.google.gson.annotations.SerializedName

data class Flag(
    @SerializedName("nearest-station")var nearestStation: String,
    @SerializedName("units")var units: String
)