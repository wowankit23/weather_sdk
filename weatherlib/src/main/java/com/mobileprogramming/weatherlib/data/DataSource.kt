package com.mobileprogramming.weatherlib.data

import com.mobileprogramming.weatherlib.data.model.weather.currentmodels.WeatherResponse
import com.mobileprogramming.weatherlib.data.model.weather.weekmodels.WeatherWeekResponse
import io.reactivex.Single

internal interface DataSource {

    fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        apiKey: String
    ): Single<WeatherResponse>

    fun getCurrentWeatherforWeek(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        exclude: String,
        cnt: Int,
        apiKey: String
    ): Single<WeatherWeekResponse>

}