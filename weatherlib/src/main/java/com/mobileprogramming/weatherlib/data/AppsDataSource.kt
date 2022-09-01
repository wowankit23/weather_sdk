package com.mobileprogramming.weatherlib.data

import com.mobileprogramming.weatherlib.data.model.weather.currentmodels.WeatherResponse
import com.mobileprogramming.weatherlib.data.model.weather.weekmodels.WeatherWeekResponse
import com.mobileprogramming.weatherlib.data.network.ApiRestServiceProvider
import com.mobileprogramming.weatherlib.data.network.ApiService
import io.reactivex.Single

internal class AppsDataSource : DataSource {
    private val apiService: ApiService =
        ApiRestServiceProvider().provideApiservice()

    override fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        apiKey: String
    ): Single<WeatherResponse> {
        return apiService.getCurrentWeather(latitude, longitude, tempUnit, apiKey)
    }

    override fun getCurrentWeatherforWeek(
        latitude: Double,
        longitude: Double,
        tempUnit: String,
        exclude: String,
        cnt: Int,
        apiKey: String
    ): Single<WeatherWeekResponse> {
        return apiService.getCurrentWeatherforWeek(latitude, longitude, tempUnit, exclude, cnt, apiKey)
    }
}