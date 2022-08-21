package com.mobileprogramming.weather_sdk.model

import com.google.gson.annotations.SerializedName


data class WeatherResponse(

    @SerializedName("lat") var lat: Double? = null,
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("timezone") var timezone: String? = null,
    @SerializedName("timezone_offset") var timezoneOffset: Int? = null,
    @SerializedName("current") var current: Current? = Current(),
    @SerializedName("daily") var daily: ArrayList<Daily> = arrayListOf()

)